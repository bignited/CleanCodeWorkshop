package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.annotation.API;
import org.example.annotation.EndPoint;
import org.example.service.SimulationService;

@API(endpoint = "/simulation/")
public class SimulationController implements IController {
    private final SimulationService simulationService;

    public SimulationController() {
        this.simulationService = new SimulationService();
    }

    @EndPoint(value = "start")
    public void startSimulation(HttpExchange exchange) {
        simulationService.runSimulation();
        sendResponse(exchange, 200, "Simulation started!");
    }

    @EndPoint(value = "createBookstores")
    public void runBookStoreCreationSimulation(HttpExchange exchange) {
        simulationService.runBookStoreCreationSimulation();
        sendResponse(exchange, 201, "Bookstores created!");
    }

    @EndPoint(value = "addBooksToBookstore")
    public void runBookStoreAddBooksSimulation(HttpExchange exchange) {
        simulationService.runBookStoreAddBooksSimulation();
        sendResponse(exchange, 200, "Books added to bookstore!");
    }

    @EndPoint(value = "getAllBooksFilteredByTitle")
    public void runFilterBooksByTitleSimulation(HttpExchange exchange) {
        simulationService.runFilterBooksByTitleSimulation();
        sendResponse(exchange, 200, "Get All filtered books.");
    }

    @EndPoint(value = "getTopRatedBooks")
    public void runTopRatedBooksSimulation(HttpExchange exchange) {
        simulationService.runTopRatedBooksSimulation();
        sendResponse(exchange, 200, "Get All top rated books.");
    }

    @EndPoint(value = "updateBooks")
    public void runUpdateBookSimulation(HttpExchange exchange) {
        simulationService.runUpdateBookSimulation();
        sendResponse(exchange, 200, "Update books.");
    }

    @EndPoint(value = "removeBooks")
    public void runRemoveBookSimulation(HttpExchange exchange) {
        simulationService.runRemoveBookSimulation();
        sendResponse(exchange, 200, "Remove books.");
    }

    @EndPoint(value = "getAllBooksOrderedByPrice")
    public void runOrderBooksByPriceSimulation(HttpExchange exchange) {
        simulationService.runOrderBooksByPriceSimulation();
        sendResponse(exchange, 200, "Get all books ordered by price.");
    }

    @EndPoint(value = "getTotalAmountOfBooks")
    public void runGetTotalBooksFromStoreSimulation(HttpExchange exchange) {
        simulationService.runGetTotalBooksFromStoreSimulation();
        sendResponse(exchange, 200, "Get total amount of books.");
    }

    @EndPoint(value = "getTotalBookPrice")
    public void runGetTotalBookPriceFromBookStoreSimulation(HttpExchange exchange) {
        simulationService.runGetTotalBookPriceFromBookStoreSimulation();
        sendResponse(exchange, 200, "Get total price of all books together.");
    }

    @EndPoint(value = "getAverageBookPrice")
    public void runGetAverageBookPriceFromBookStoreSimulation(HttpExchange exchange) {
        simulationService.runGetAverageBookPriceFromBookStoreSimulation();
        sendResponse(exchange, 200, "Get average price of a book.");
    }

    @EndPoint(value = "sellBooks")
    public void runSellBookSimulation(HttpExchange exchange) {
        simulationService.runSellBookSimulation();
        sendResponse(exchange, 200, "Sell books.");
    }

    @EndPoint(value = "getTotalRevenue")
    public void runTotalRevenueSimulation(HttpExchange exchange) {
        simulationService.runTotalRevenueSimulation();
        sendResponse(exchange, 200, "Get total revenue.");
    }

    @EndPoint(value = "getTotalBooksSold")
    public void runTotalBooksSoldSimulation(HttpExchange exchange) {
        simulationService.runTotalBooksSoldSimulation();
        sendResponse(exchange, 200, "Get total amount of books sold.");
    }

    @EndPoint(value = "getAllBooksOrderedByRating")
    public void runBooksByRatingRangeSimulation(HttpExchange exchange) {
        simulationService.runBooksByRatingRangeSimulation();
        sendResponse(exchange, 200, "Get all books ordered by rating.");
    }

    public void runBooksSortedByPriceSimulation(HttpExchange exchange) {
        simulationService.runBooksSortedByPriceSimulation();
        sendResponse(exchange, 200, "Run simulation.");
    }
}
