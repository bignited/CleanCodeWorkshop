package org.example.config;

import com.sun.net.httpserver.HttpServer;
import org.example.service.LoggingService;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Configuration class for setting up a web application server.
 * This class provides methods to start the application and set up the HTTP server.
 */
public class AppConfig {
    private static final int SERVER_PORT = 8080;
    private static HttpServer httpServer;

    /**
     * Sets-up a web-application server with the generated endpoints.
     * <p>
     * The server is launched and kept online.
     */
    public static void startApplication() {
        setUpServer();
        EndpointRegistrar.registerEndpoints(httpServer);
        httpServer.setExecutor(null);
        httpServer.start();
    }

    /**
     * Sets up the HTTP server.
     * <p>
     * Attempts to create an HTTP server instance bound to the specified port {@code SERVER_PORT}.
     *
     * @throws IOException if an I/O error occurs when creating the server socket.
     *                     For example, if the port is already in use.
     */
    private static void setUpServer() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        } catch (IOException e) {
            LoggingService.logInfo("Server could not be created.:" + e.getMessage(), AppConfig.class);
        }
    }
}
