package com.thecoderscorner.embedcontrol.jfx.controlmgr;

import com.thecoderscorner.embedcontrol.core.controlmgr.ComponentSettings;
import com.thecoderscorner.embedcontrol.core.controlmgr.MenuComponentControl;
import com.thecoderscorner.embedcontrol.core.controlmgr.ThreadMarshaller;
import com.thecoderscorner.menu.domain.AnalogMenuItem;
import com.thecoderscorner.menu.domain.MenuItem;
import com.thecoderscorner.menu.domain.state.AnyMenuState;
import com.thecoderscorner.menu.domain.state.MenuState;
import com.thecoderscorner.menu.domain.state.MenuTree;
import com.thecoderscorner.menu.domain.util.MenuItemFormatter;
import com.thecoderscorner.menu.domain.util.MenuItemHelper;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.thecoderscorner.embedcontrol.core.controlmgr.color.ConditionalColoring.ColorComponentType;
import static com.thecoderscorner.embedcontrol.core.controlmgr.color.ControlColor.asFxColor;

public class HorizontalSliderAnalogComponent extends JfxTextEditorComponentBase<Integer> {
    private RenderingStatus lastStatus = RenderingStatus.NORMAL;
    private final MenuTree tree;
    private double displayWidth;
    private Canvas canvas;

    public HorizontalSliderAnalogComponent(MenuComponentControl controller, ComponentSettings settings, MenuItem item, MenuTree tree, ThreadMarshaller marshaller) {
        super(controller, settings, item, marshaller);
        this.tree = tree;
    }

    @Override
    public Node createComponent() {
        canvas = new Canvas();
        if(isItemEditable(item)) {
            canvas.setOnMouseReleased(mouseEvent -> sendItemAbsolute());
            canvas.setOnMouseDragged(mouseEvent -> onMouseAdjusted(mouseEvent.getX()));
        }
        return canvas;
    }


    private void onMouseAdjusted(double newPositionInControl) {
        AnyMenuState menuState = tree.getMenuState(item);
        if ((menuState == null) || !(item instanceof AnalogMenuItem analog))return;

        var oneTick = displayWidth / (double)analog.getMaxValue();
        var value = Math.max(0, Math.min(analog.getMaxValue(), newPositionInControl / oneTick));
        AnyMenuState newState = MenuItemHelper.stateForMenuItem(item, value, true, menuState.isActive());
        tree.changeItem(item, newState);
        onItemUpdated((MenuState<?>) newState);
        currentVal = (int)value;

        onPaintSurface(canvas.getGraphicsContext2D());
    }

    private void sendItemAbsolute() {
        if (status == RenderingStatus.EDIT_IN_PROGRESS) return;
        try {
            if (tree.getMenuState(item) != null)
            {
                var correlation = componentControl.editorUpdatedItem(item, currentVal);
                editStarted(correlation);
            }
        } catch (Exception ex) {
            logger.log(System.Logger.Level.ERROR, "State problem in slider", ex);
        }
    }

    protected void onPaintSurface(GraphicsContext gc) {

        var analog = (AnalogMenuItem) item;

        displayWidth = (int) canvas.getWidth();

        var currentPercentage = currentVal / (float) analog.getMaxValue();

        gc.setFill(asFxColor(getDrawingSettings().getColors().backgroundFor(RenderingStatus.NORMAL, ColorComponentType.HIGHLIGHT)));
        gc.fillRect(0, 0, displayWidth * currentPercentage, canvas.getHeight());

        gc.setFill(asFxColor(getDrawingSettings().getColors().backgroundFor(lastStatus, ColorComponentType.BUTTON)));
        gc.fillRect(displayWidth * currentPercentage, 0, displayWidth, canvas.getHeight());

        gc.setFill(asFxColor(getDrawingSettings().getColors().foregroundFor(lastStatus, ColorComponentType.HIGHLIGHT)));

        String toDraw = "";
        if(controlTextIncludesName()) toDraw = MenuItemFormatter.defaultInstance().getItemName(item);
        if(controlTextIncludesValue()) toDraw += " " + MenuItemFormatter.defaultInstance().formatForDisplay(item, currentVal);
        final Text textObj = new Text(toDraw);
        gc.setFill(asFxColor(getDrawingSettings().getColors().foregroundFor(lastStatus, ColorComponentType.BUTTON)));
        gc.setFont(Font.font(gc.getFont().getFamily(), getDrawingSettings().getFontSize()));
        var bounds = textObj.getLayoutBounds();
        gc.fillText(toDraw, (displayWidth - bounds.getWidth()) / 2.0, (canvas.getHeight() - (bounds.getHeight() / 2.0)));
    }

    @Override
    public void changeControlSettings(RenderingStatus status, String text) {
        lastStatus = status;
        onPaintSurface(canvas.getGraphicsContext2D());
    }
}