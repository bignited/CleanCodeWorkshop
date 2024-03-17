package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.controller.SimulationController;
import org.example.service.LoggingService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private static final int SERVER_PORT = 8080;
    private static final SimulationController simulationController = new SimulationController();

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
            server.createContext("/simulation/start", (simulationController::startSimulation));
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            LoggingService.logInfo("Server could not be started. Exiting...:" + e.getMessage(), Main.class);
        }
    }
}