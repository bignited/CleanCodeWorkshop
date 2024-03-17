package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.service.LoggingService;
import org.example.service.SimulationService;

import java.io.IOException;

public class SimulationController {
    private final SimulationService simulationService;

    public SimulationController() {
        this.simulationService = new SimulationService();
    }

    public void startSimulation(HttpExchange exchange) {
        simulationService.runSimulation();
        try {
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } catch (IOException e) {
            LoggingService.logInfo("Could not send response to client for API call /simulation/start: "
                    + e.getMessage(), this.getClass());
        }
    }

    public void runBookStoreCreationSimulation(HttpExchange exchange) {
        simulationService.runBookStoreCreationSimulation();
        try {
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } catch (IOException e) {
            LoggingService.logInfo("Could not send response to client for API call /simulation/bookstore/create: "
                    + e.getMessage(), this.getClass());
        }
    }
}
