package org.example;

import org.example.service.SimulationService;

public class Main {
    public static void main(String[] args) {
        SimulationService simulationService = new SimulationService();
        simulationService.runSimulation();
    }
}