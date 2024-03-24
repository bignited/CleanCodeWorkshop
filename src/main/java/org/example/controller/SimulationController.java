package org.example.controller;

import com.sun.net.httpserver.HttpHandler;
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
    public HttpHandler startSimulation() {
        return exchange -> {
            simulationService.runSimulation();
            sendResponse(exchange, 200, "Simulation started!");
        };
    }

    @EndPoint(value = "createBookstores")
    public HttpHandler runBookStoreCreationSimulation() {
        return exchange -> {
            simulationService.runBookStoreCreationSimulation();
            sendResponse(exchange, 201, "Bookstores created!");
        };
    }

    @EndPoint(value = "addBooksToBookstore")
    public HttpHandler runBookStoreAddBooksSimulation() {
        return exchange -> {
            simulationService.runBookStoreAddBooksSimulation();
            sendResponse(exchange, 200, "Books added to bookstore!");
        };
    }

    @EndPoint(value = "getAllBooksFilteredByTitle")
    public HttpHandler runFilterBooksByTitleSimulation() {
        return exchange -> {
            simulationService.runFilterBooksByTitleSimulation();
            sendResponse(exchange, 200, "Get All filtered books.");
        };
    }

    @EndPoint(value = "getTopRatedBooks")
    public HttpHandler runTopRatedBooksSimulation() {
        return exchange -> {
            simulationService.runTopRatedBooksSimulation();
            sendResponse(exchange, 200, "Get All top rated books.");
        };
    }

    @EndPoint(value = "updateBooks")
    public HttpHandler runUpdateBookSimulation() {
        return exchange -> {
            simulationService.runTopRatedBooksSimulation();
            sendResponse(exchange, 200, "Get All top rated books.");
        };
    }

    @EndPoint(value = "removeBooks")
    public HttpHandler runRemoveBookSimulation() {
        return exchange -> {
            simulationService.runRemoveBookSimulation();
            sendResponse(exchange, 200, "Remove books.");
        };
    }

    @EndPoint(value = "getAllBooksOrderedByPrice")
    public HttpHandler runOrderBooksByPriceSimulation() {
        return exchange -> {
            simulationService.runOrderBooksByPriceSimulation();
            sendResponse(exchange, 200, "Get all books ordered by price.");
        };
    }

    @EndPoint(value = "getTotalAmountOfBooks")
    public HttpHandler runGetTotalBooksFromStoreSimulation() {
        return exchange -> {
            simulationService.runGetTotalBooksFromStoreSimulation();
            sendResponse(exchange, 200, "Get total amount of books.");
        };
    }

    @EndPoint(value = "getTotalBookPrice")
    public HttpHandler runGetTotalBookPriceFromBookStoreSimulation() {
        return exchange -> {
            simulationService.runGetTotalBookPriceFromBookStoreSimulation();
            sendResponse(exchange, 200, "Get total price of all books together.");
        };
    }

    @EndPoint(value = "getAverageBookPrice")
    public HttpHandler runGetAverageBookPriceFromBookStoreSimulation() {
        return exchange -> {
            simulationService.runGetAverageBookPriceFromBookStoreSimulation();
            sendResponse(exchange, 200, "Get average price of a book.");
        };
    }

    @EndPoint(value = "sellBooks")
    public HttpHandler runSellBookSimulation() {
        return exchange -> {
            simulationService.runSellBookSimulation();
            sendResponse(exchange, 200, "Sell books.");
        };
    }

    @EndPoint(value = "getTotalRevenue")
    public HttpHandler runTotalRevenueSimulation() {
        return exchange -> {
            simulationService.runTotalRevenueSimulation();
            sendResponse(exchange, 200, "Get total revenue.");
        };
    }

    @EndPoint(value = "getTotalBooksSold")
    public HttpHandler runTotalBooksSoldSimulation() {
        return exchange -> {
            simulationService.runTotalBooksSoldSimulation();
            sendResponse(exchange, 200, "get total books sold.");
        };
    }

    @EndPoint(value = "getAllBooksOrderedByRating")
    public HttpHandler runBooksByRatingRangeSimulation() {
        return exchange -> {
            simulationService.runBooksByRatingRangeSimulation();
            sendResponse(exchange, 200, "Get all books ordered by rating.");
        };
    }

    public HttpHandler runBooksSortedByPriceSimulation() {
        return exchange -> {
            simulationService.runBooksSortedByPriceSimulation();
            sendResponse(exchange, 200, "Get all books sorted by price.");
        };
    }
}
