package com.thecoderscorner.embedcontrol.jfxapp;

import com.thecoderscorner.embedcontrol.core.creators.ConnectionCreator;
import com.thecoderscorner.embedcontrol.core.serial.PlatformSerialFactory;
import com.thecoderscorner.menu.persist.JsonMenuItemSerializer;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Provides support functions that can be used by other components within the embedCONTROL app.
 */
public interface EmbedControlContext {
    /**
     * Gets the global executor service.
     * @return the global executor service
     */
    ScheduledExecutorService getExecutorService();

    /**
     * Gets the global instance of the JSON serializer
     * @return the JSON serializer
     */
    JsonMenuItemSerializer getSerializer();

    /**
     * Gets an instance of the serial factory for creating new serial connections
     * @return a serial factory for creating connections
     */
    PlatformSerialFactory getSerialFactory();

    /**
     * Create a new connection from the creator provided.
     * @param connectionCreator the creator object to create the underlying connection
     */
    void createConnection(ConnectionCreator connectionCreator);

    /**
     * Delete the connection WITHOUT any user interaction. Must be called on UI thread.
     * @param identifier the connection to completely delete
     */
    void deleteConnection(UUID identifier);
}
