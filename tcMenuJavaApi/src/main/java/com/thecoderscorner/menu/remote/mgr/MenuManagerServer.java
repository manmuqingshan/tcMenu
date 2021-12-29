package com.thecoderscorner.menu.remote.mgr;

import com.thecoderscorner.menu.auth.MenuAuthenticator;
import com.thecoderscorner.menu.domain.AnalogMenuItem;
import com.thecoderscorner.menu.domain.EnumMenuItem;
import com.thecoderscorner.menu.domain.MenuItem;
import com.thecoderscorner.menu.domain.state.IntegerMenuState;
import com.thecoderscorner.menu.domain.state.MenuState;
import com.thecoderscorner.menu.domain.state.MenuTree;
import com.thecoderscorner.menu.domain.state.StringListMenuState;
import com.thecoderscorner.menu.domain.util.MenuItemHelper;
import com.thecoderscorner.menu.remote.commands.*;
import com.thecoderscorner.menu.remote.protocol.ApiPlatform;
import com.thecoderscorner.menu.remote.protocol.CorrelationId;

import java.time.Clock;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.thecoderscorner.menu.remote.commands.MenuChangeCommand.ChangeType;
import static com.thecoderscorner.menu.remote.commands.MenuHeartbeatCommand.HeartbeatMode;

public class MenuManagerServer implements NewServerConnectionListener {
    private final System.Logger logger = System.getLogger(MenuManagerServer.class.getSimpleName());
    private final ScheduledExecutorService executorService;
    private final MenuTree tree;
    private final ServerConnectionManager serverManager;
    private final String serverName;
    private final UUID serverUuid;
    private final MenuAuthenticator authenticator;
    private final AtomicBoolean successfulLogin = new AtomicBoolean(false);
    private final Clock clock;
    private final AtomicBoolean pairingMode = new AtomicBoolean(false);
    private ScheduledFuture<?> hbSchedule;

    public MenuManagerServer(ScheduledExecutorService executorService, MenuTree tree, ServerConnectionManager serverManager,
                             String serverName, UUID uuid, MenuAuthenticator authenticator, Clock clock) {
        this.executorService = executorService;
        this.tree = tree;
        this.serverManager = serverManager;
        this.serverName = serverName;
        this.serverUuid = uuid;
        this.authenticator = authenticator;
        this.clock = clock;
    }

    public void start() {
        serverManager.start(this);
        hbSchedule = executorService.scheduleAtFixedRate(this::checkHeartbeats, 200, 200, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if(hbSchedule != null) hbSchedule.cancel(false);
        try {
            serverManager.stop();
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Server manager threw error during stop", e);
        }
    }

    private void checkHeartbeats() {
        for (var socket : serverManager.getServerConnections()) {
            if((clock.millis() - socket.lastReceivedHeartbeat())  > (socket.getHeartbeatFrequency() * 3L)) {
                logger.log(System.Logger.Level.WARNING, "HB timeout, no received message within frequency");
                socket.closeConnection();
            }
            else if((clock.millis() - socket.lastTransmittedHeartbeat()) > socket.getHeartbeatFrequency()) {
                logger.log(System.Logger.Level.INFO, "Sending HB due to inactivity");
                socket.sendCommand(new MenuHeartbeatCommand(socket.getHeartbeatFrequency(), HeartbeatMode.NORMAL));
            }
        }
    }

    @Override
    public void connectionCreated(ServerConnection connection) {
        connection.registerMessageHandler(this::messageReceived);
        connection.sendCommand(new MenuHeartbeatCommand(connection.getHeartbeatFrequency(), HeartbeatMode.START));
    }

    private void messageReceived(ServerConnection conn, MenuCommand cmd) {
        try {
            if(pairingMode.get()) return; // nothing further is done on a pairing connection.
            switch(cmd.getCommandType()) {
                case JOIN: {
                    var join = (MenuJoinCommand) cmd;
                    if (authenticator != null && !authenticator.authenticate(join.getMyName(), join.getAppUuid())) {
                        logger.log(System.Logger.Level.WARNING, "Invalid credentials from " + join.getMyName());
                        conn.sendCommand(new MenuAcknowledgementCommand(CorrelationId.EMPTY_CORRELATION, AckStatus.INVALID_CREDENTIALS));
                        conn.closeConnection();
                    }
                    else {
                        successfulLogin.set(true);
                        logger.log(System.Logger.Level.WARNING, "Successful login from " + join.getMyName());
                        conn.sendCommand(new MenuAcknowledgementCommand(CorrelationId.EMPTY_CORRELATION, AckStatus.SUCCESS));
                        conn.sendCommand(new MenuBootstrapCommand(MenuBootstrapCommand.BootType.START));

                        tree.recurseTreeIteratingOnItems(MenuTree.ROOT, (item, parent) -> {
                            if (!item.isLocalOnly()) {
                                var bootMsg = MenuItemHelper.getBootMsgForItem(item, parent, tree);
                                bootMsg.ifPresent(conn::sendCommand);
                            }
                        });

                        conn.sendCommand(new MenuBootstrapCommand(MenuBootstrapCommand.BootType.END));
                    }
                    break;
                }
                case PAIRING_REQUEST:
                    startPairingMode(conn, (MenuPairingCommand)cmd);
                    break;
                case HEARTBEAT: {
                    var hb = (MenuHeartbeatCommand) cmd;
                    if(hb.getMode() == HeartbeatMode.START) {
                        conn.sendCommand(new MenuJoinCommand(serverUuid, serverName, ApiPlatform.JAVA_API, 1));
                    }
                    break;
                }
                case CHANGE_INT_FIELD:
                    if(!successfulLogin.get()) {
                        logger.log(System.Logger.Level.WARNING, "Un-authenticated change command ignored");
                        return;
                    }
                    handleIncomingChange(conn, (MenuChangeCommand) cmd);
                    break;
            }
        } catch (Exception e) {
            conn.closeConnection();
        }
    }

    private void startPairingMode(ServerConnection conn, MenuPairingCommand cmd) {
        pairingMode.set(true);
        var success = authenticator.addAuthentication(cmd.getName(), cmd.getUuid());
        var determinedStatus = success ? AckStatus.SUCCESS : AckStatus.INVALID_CREDENTIALS;
        conn.sendCommand(new MenuAcknowledgementCommand(CorrelationId.EMPTY_CORRELATION, determinedStatus));
    }

    public MenuTree getManagedMenu() {
        return tree;
    }

    public void updateMenuItem(MenuItem item, Object newValue) {
        MenuItemHelper.setMenuState(item, newValue, tree);
        menuItemDidUpdate(item);
    }

    public void menuItemDidUpdate(MenuItem item) {
        logger.log(System.Logger.Level.INFO, "Sending item update for " + item);
        var state = tree.getMenuState(item);
        if(state == null) return;

        MenuCommand cmd;
        if(state instanceof StringListMenuState) {
            cmd = new MenuChangeCommand(CorrelationId.EMPTY_CORRELATION, item.getId(), ((StringListMenuState)state).getValue());
        }
        else {
            cmd = new MenuChangeCommand(CorrelationId.EMPTY_CORRELATION, item.getId(), ChangeType.ABSOLUTE,
                    state.getValue().toString());
        }

        for(var socket : serverManager.getServerConnections()) {
            socket.sendCommand(cmd);
        }
    }

    private void handleIncomingChange(ServerConnection socket, MenuChangeCommand cmd) {
        var maybeItem = tree.getMenuById(cmd.getMenuItemId());
        if(maybeItem.isEmpty()) {
            socket.sendCommand(new MenuAcknowledgementCommand(cmd.getCorrelationId(), AckStatus.ID_NOT_FOUND));
            return;
        }
        var item = maybeItem.get();

        if(cmd.getChangeType() == ChangeType.DELTA) {
            MenuState<Integer> state = tree.getMenuState(item);
            if(state == null) state = new IntegerMenuState(item, true, false, 0);
            int val = state.getValue() + Integer.parseInt(cmd.getValue());
            if(val <= 0 || (item instanceof AnalogMenuItem && val > ((AnalogMenuItem) item).getMaxValue()) ||
                    (item instanceof EnumMenuItem && val >= ((EnumMenuItem) item).getEnumEntries().size())) {
                socket.sendCommand(new MenuAcknowledgementCommand(cmd.getCorrelationId(), AckStatus.VALUE_RANGE_WARNING));
                return;
            }
            state = new IntegerMenuState(item, state.isChanged(), state.isActive(), val);
            tree.changeItem(item, state);
            sendChangeAndAck(socket, item, val, cmd.getCorrelationId());
        } else if(cmd.getChangeType() == ChangeType.ABSOLUTE) {
            var newState = MenuItemHelper.stateForMenuItem(tree.getMenuState(item), item, cmd.getValue());
            tree.changeItem(item, newState);
            sendChangeAndAck(socket, item, cmd.getValue(), cmd.getCorrelationId());
        }
    }

    private void sendChangeAndAck(ServerConnection socket, MenuItem item, Object val, CorrelationId correlationId) {
        socket.sendCommand(new MenuAcknowledgementCommand(correlationId, AckStatus.SUCCESS));
        socket.sendCommand(new MenuChangeCommand(CorrelationId.EMPTY_CORRELATION, item.getId(), ChangeType.ABSOLUTE, val.toString()));
    }

    public void reportDialogUpdate(DialogMode show, String title, String content, MenuButtonType b1, MenuButtonType b2) {
        var cmd = new MenuDialogCommand(show, title, content, b1, b2, CorrelationId.EMPTY_CORRELATION);
        for(var socket : serverManager.getServerConnections()) {
            socket.sendCommand(cmd);
        }
    }
}
