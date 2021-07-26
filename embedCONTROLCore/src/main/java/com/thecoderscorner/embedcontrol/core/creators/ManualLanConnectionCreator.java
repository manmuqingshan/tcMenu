package com.thecoderscorner.embedcontrol.core.creators;

import com.thecoderscorner.embedcontrol.core.service.GlobalSettings;
import com.thecoderscorner.menu.remote.AuthStatus;
import com.thecoderscorner.menu.remote.RemoteMenuController;
import com.thecoderscorner.menu.remote.protocol.TagValMenuCommandProtocol;
import com.thecoderscorner.menu.remote.socket.SocketControllerBuilder;

import java.time.Clock;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.prefs.Preferences;

public class ManualLanConnectionCreator implements ConnectionCreator {
    private final GlobalSettings settings;
    private String name;
    private String ipAddr;
    private int port;
    private RemoteMenuController controller;
    private ScheduledExecutorService executorService;

    public ManualLanConnectionCreator(GlobalSettings settings, ScheduledExecutorService executorService) {
        this.settings = settings;
        this.executorService = executorService;
        name = ipAddr = "";
        port = 0;
    }

    public ManualLanConnectionCreator(GlobalSettings settings, ScheduledExecutorService executorService,
                                      String name, String ipAddr, int port) {
        this.settings = settings;
        this.executorService = executorService;
        this.name = name;
        this.ipAddr = ipAddr;
        this.port = port;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AuthStatus currentState() {
        return controller != null ? controller.getConnector().getAuthenticationStatus() : AuthStatus.NOT_STARTED;
    }

    @Override
    public RemoteMenuController start() throws Exception {
        controller = new SocketControllerBuilder()
                .withAddress(ipAddr)
                .withPort(port)
                .withLocalName(settings.getAppName())
                .withUUID(UUID.fromString(settings.getAppUuid()))
                .withProtocol(new TagValMenuCommandProtocol())
                .withClock(Clock.systemDefaultZone())
                .withExecutor(executorService)
                .build();

        controller.start();
        return controller;
    }

    @Override
    public void load(Preferences prefs) {
        name = prefs.get("name", "");
        ipAddr = prefs.get("ipAddr", "");
        port = prefs.getInt("port", 0);
    }

    @Override
    public void save(Preferences prefs) {
        prefs.put("name", name);
        prefs.put("ipAddr", ipAddr);
        prefs.putInt("port", port);
    }
}
