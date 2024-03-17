package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
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
            throw new RuntimeException(e);
        }
    }
}
