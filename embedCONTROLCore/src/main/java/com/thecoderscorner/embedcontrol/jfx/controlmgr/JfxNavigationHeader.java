package com.thecoderscorner.embedcontrol.jfx.controlmgr;

import com.thecoderscorner.embedcontrol.core.controlmgr.MenuComponentControl;
import com.thecoderscorner.embedcontrol.core.controlmgr.PanelPresentable;
import com.thecoderscorner.embedcontrol.core.controlmgr.TreeComponentManager;
import com.thecoderscorner.embedcontrol.customization.ScreenLayoutPersistence;
import com.thecoderscorner.menu.domain.SubMenuItem;
import com.thecoderscorner.menu.domain.state.MenuTree;
import com.thecoderscorner.menu.mgr.DialogManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

import static javafx.scene.control.Alert.AlertType;

public class JfxNavigationHeader implements TitleWidgetListener<Image>, JfxNavigationManager {
    public enum StandardLedWidgetStates { RED, ORANGE, GREEN }
    public enum StandardWifiWidgetStates { NOT_CONNECTED, LOW_SIGNAL, FAIR_SIGNAL, MEDIUM_SIGNAL, GOOD_SIGNAL }

    private final System.Logger logger = System.getLogger(getClass().getSimpleName());
    private final Map<TitleWidget<Image>, Button> widgetButtonMap = new HashMap<>();
    private final List<BiConsumer<ActionEvent, TitleWidget<Image>>> widgetClickListeners = new CopyOnWriteArrayList<>();
    private final LinkedList<PanelPresentable<Node>> navigationStack = new LinkedList<>();
    private final ScreenLayoutPersistence layoutPersistence;
    private TreeComponentManager<Node> treeComponentManager;
    private ScrollPane managedNavArea;
    private Label titleArea;
    private Button leftButton;
    private HBox widgetPane;
    private MenuComponentControl controller;
    private DialogManager dialogManager;
    private JfxPanelLayoutEditorPresenter itemEditorPresenter;

    public JfxNavigationHeader(ScreenLayoutPersistence persistence) {
        this.layoutPersistence = persistence;
    }

    public void initialiseUI(TreeComponentManager<Node> treeComponentManager, DialogManager dialogManager, MenuComponentControl control, ScrollPane managedNavArea) {
        this.dialogManager = dialogManager;
        this.managedNavArea = managedNavArea;
        this.controller = control;
        this.treeComponentManager = treeComponentManager;
    }

    public Node initialiseControls() {
        GridPane grid = new GridPane();
        titleArea = new Label("");
        titleArea.setMaxWidth(Double.MAX_VALUE);
        titleArea.setStyle("-fx-font-size: 18px;-fx-text-alignment: left;");
        grid.setPadding(new Insets(4));
        grid.getColumnConstraints().clear();
        grid.getColumnConstraints().addAll(
                new ColumnConstraints(25, 25, 120, Priority.ALWAYS, HPos.LEFT, false),
                new ColumnConstraints(100, 800, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true),
                new ColumnConstraints(32, 140, Double.MAX_VALUE, Priority.ALWAYS, HPos.RIGHT, false)
        );
        leftButton = new Button();
        leftButton.setOnAction(e -> {
            if (navigationStack.size() > 1 && navigationStack.peek().canClose()) {
                popNavigation();
            }
        });
        leftButton.setStyle("-fx-background-color: #295c95;");
        grid.add(leftButton, 0, 0);
        grid.add(titleArea, 1, 0);
        grid.setStyle("-fx-background-color: #0f7ab0;");
        widgetPane = new HBox(4);
        grid.add(widgetPane, 2, 0);
        return grid;
    }

    @Override
    public void setTitle(String newTitle) {
        titleArea.setText(newTitle);
    }

    @Override
    public void addTitleWidget(TitleWidget<Image> widget) {
        if(!Platform.isFxApplicationThread()) throw new IllegalStateException("Always call on JavaFX thread see - Platform.runLater(..)");
        var widgetBtn = new Button();
        widgetBtn.setStyle("-fx-background-color: #295c95;");
        var img = widget.getCurrentImage();
        double scaleFactor = img.getWidth() / 16;
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(16);
        imgView.setFitHeight(img.getHeight() / scaleFactor);
        widgetBtn.setGraphic(imgView);
        widgetBtn.setOnAction((evt) -> fireWidgetClicked(evt, widget));
        widgetPane.getChildren().add(widgetBtn);
        widgetButtonMap.put(widget, widgetBtn);
        widget.addWidgetChangeListener(this);
    }

    private void fireWidgetClicked(ActionEvent evt, TitleWidget<Image> widget) {
        if(evt.getSource() instanceof Button btn && btn.getContextMenu() != null) {
            var ctxMenu = btn.getContextMenu();
            ctxMenu.show(btn, Side.BOTTOM, PopupControl.USE_COMPUTED_SIZE, PopupControl.USE_COMPUTED_SIZE);
        } else {
            for (var l : widgetClickListeners) {
                l.accept(evt, widget);
            }
        }
    }

    @Override
    public void addWidgetClickedListener(BiConsumer<ActionEvent, TitleWidget<Image>> listener) {
        widgetClickListeners.add(listener);
    }

    @Override
    public DialogManager getDialogManager() {
        return dialogManager;
    }

    @Override
    public void titleWidgetHasChanged(TitleWidget<Image> widget) {
        Platform.runLater(() -> {
            var widGfx = widgetButtonMap.get(widget);
            if(widGfx == null) return;
            var imgView = (ImageView) widGfx.getGraphic();
            imgView.setImage(widget.getCurrentImage());
        });
    }

    public Optional<Button> getButtonFor(TitleWidget<Image> widget) {
        return Optional.ofNullable(widgetButtonMap.get(widget));
    }

    @Override
    public void pushMenuNavigation(SubMenuItem subMenuItem, boolean resetNavigation) {
        Platform.runLater(() -> {
            var controlGrid = new JfxMenuControlGrid(controller, Platform::runLater, treeComponentManager, dialogManager, layoutPersistence, subMenuItem);
            if(itemEditorPresenter != null)  controlGrid.setLayoutEditor(itemEditorPresenter);
            if(resetNavigation) {
                navigationStack.clear();
            }
            runNavigation(controlGrid);
            navigationStack.push(controlGrid);
        });
    }

    @Override
    public void pushMenuNavigation(SubMenuItem asSubMenu) {
        pushMenuNavigation(asSubMenu, false);
    }

    public void pushNavigation(PanelPresentable<Node> navigation) {
        Platform.runLater(() -> {
            runNavigation(navigation);
            navigationStack.push(navigation);
        });
    }

    private void runNavigation(PanelPresentable<Node> navigation) {
        try {
            setTitle(navigation.getPanelName());
            managedNavArea.setContent(navigation.getPanelToPresent(managedNavArea.getWidth()));
            leftButton.setVisible(navigation.canClose());
            leftButton.setManaged(navigation.canClose());
            leftButton.setText("<");

        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Did not navigate to panel " + navigation.getPanelName(), e);
            Alert alert = new Alert(AlertType.ERROR, "Navigation failed to " + navigation.getPanelName(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    public void popNavigation() {
        Platform.runLater(() -> {
            var navigation = navigationStack.pop();
            navigation.closePanel();
            runNavigation(navigationStack.peek());
        });
    }

    public void resetNavigationTo(PanelPresentable<Node> navigation) {
        Platform.runLater(() -> {
            navigationStack.clear();
            runNavigation(navigation);
            navigationStack.push(navigation);
        });
    }

    @Override
    public PanelPresentable<Node> currentNavigationPanel() {
        return navigationStack.peek();
    }

    @Override
    public void setItemEditorPresenter(JfxPanelLayoutEditorPresenter panelPresenter) {
        this.itemEditorPresenter = panelPresenter;
        if(!navigationStack.isEmpty()) {
            navigationStack.clear();
            pushMenuNavigation(MenuTree.ROOT);
        }
    }

    public void destroy() {
        for(var nav : navigationStack) {
            nav.closePanel();
        }
        navigationStack.clear();
        widgetButtonMap.clear();
        widgetClickListeners.clear();
    }

    /**
     * Creates a 5 level WiFi widget that represents, no connection, poor connection, low strength, fair strength
     * and a good connection. The first icon is no connection, the last is good connection.
     * @return the widget
     */
    public static TitleWidget<Image> standardWifiWidget() {
        return JfxNavigationHeader.widgetFromImages(
                JfxNavigationHeader.class.getResource("/img/con-fail.png"),
                JfxNavigationHeader.class.getResource("/img/wifi-poor.png"),
                JfxNavigationHeader.class.getResource("/img/wifi-low.png"),
                JfxNavigationHeader.class.getResource("/img/wifi-fair.png"),
                JfxNavigationHeader.class.getResource("/img/wifi-full.png"));
    }

    /**
     * Creates a single icon that looks like a cog, suitable to represent a configuration widget.
     * @return the widget
     */
    public static TitleWidget<Image> standardSettingsWidget() {
        return JfxNavigationHeader.widgetFromImages(
                JfxNavigationHeader.class.getResource("/img/settings-cog.png")
        );
    }

    /**
     * Provides a single save icon that can be used in touch systems to perform a save operation.
     * @return a standard save widget
     */
    public static TitleWidget<Image> standardSaveWidget() {
        return JfxNavigationHeader.widgetFromImages(
                JfxNavigationHeader.class.getResource("/img/save-icon.png")
        );
    }

    /**
     * Provides a standard tri-state LED arrangement with Red, Orange and Green in that order.
     * @return a tri-state LED widget
     */
    public static TitleWidget<Image> standardStatusLedWidget() {
        return JfxNavigationHeader.widgetFromImages(
                JfxNavigationHeader.class.getResource("/img/red-led-icon.png"),
                JfxNavigationHeader.class.getResource("/img/orange-led-icon.png"),
                JfxNavigationHeader.class.getResource("/img/green-led-icon.png")
        );
    }

    /**
     * The standard widget for toggling between layout mode on and layout mode off, it has two images, image 1 is off
     * and image 2 is on.
     * @return a widget that can represent layout mode being on and off.
     */
    public static TitleWidget<Image> standardLayoutWidget() {
        return JfxNavigationHeader.widgetFromImages(
                JfxNavigationHeader.class.getResource("/img/layout-off.png"),
                JfxNavigationHeader.class.getResource("/img/layout-on.png")
        );
    }

    public static TitleWidget<Image> widgetFromImages(URL... imageName) {
        var images = Arrays.stream(imageName)
                .map(i -> new Image(i.toString()))
                .toList();
        return new TitleWidget<>(images, images.size(), 0);
    }
}
