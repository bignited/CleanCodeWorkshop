package org.example.controller;

import com.sun.net.httpserver.HttpHandler;
import org.example.annotation.API;
import org.example.annotation.EndPoint;
import org.example.model.controller.HttpMethod;
import org.example.service.SimulationService;

@API(endpoint = "/simulation/")
public class SimulationController implements IController {
    private final SimulationService simulationService;

    public SimulationController() {
        this.simulationService = new SimulationService();
    }

    @EndPoint(url = "start", method = HttpMethod.GET)
    public HttpHandler startSimulation() {
        return exchange -> {
            if(exchange.getRequestMethod().equals("GET")) {
                simulationService.runSimulation();
                sendResponse(exchange, 200, "Simulation started!");
            } else {
                sendResponse(exchange, 405, "Method not allowed!");
            }
        };
    }

    @EndPoint(url = "createBookstores", method = HttpMethod.GET)
    public HttpHandler runBookStoreCreationSimulation() {
        return exchange -> {
            simulationService.runBookStoreCreationSimulation();
            sendResponse(exchange, 201, "Bookstores created!");
        };
    }

    @EndPoint(url = "addBooksToBookstore", method = HttpMethod.GET)
    public HttpHandler runBookStoreAddBooksSimulation() {
        return exchange -> {
            simulationService.runBookStoreAddBooksSimulation();
            sendResponse(exchange, 200, "Books added to bookstore!");
        };
    }

    @EndPoint(url = "getAllBooksFilteredByTitle", method = HttpMethod.GET)
    public HttpHandler runFilterBooksByTitleSimulation() {
        return exchange -> {
            simulationService.runFilterBooksByTitleSimulation();
            sendResponse(exchange, 200, "Get All filtered books.");
        };
    }

    @EndPoint(url = "getTopRatedBooks", method = HttpMethod.GET)
    public HttpHandler runTopRatedBooksSimulation() {
        return exchange -> {
            simulationService.runTopRatedBooksSimulation();
            sendResponse(exchange, 200, "Get All top rated books.");
        };
    }

    @EndPoint(url = "updateBooks", method = HttpMethod.GET)
    public HttpHandler runUpdateBookSimulation() {
        return exchange -> {
            simulationService.runTopRatedBooksSimulation();
            sendResponse(exchange, 200, "Get All top rated books.");
        };
    }

    @EndPoint(url = "removeBooks", method = HttpMethod.GET)
    public HttpHandler runRemoveBookSimulation() {
        return exchange -> {
            simulationService.runRemoveBookSimulation();
            sendResponse(exchange, 200, "Remove books.");
        };
    }

    @EndPoint(url = "getAllBooksOrderedByPrice", method = HttpMethod.GET)
    public HttpHandler runOrderBooksByPriceSimulation() {
        return exchange -> {
            simulationService.runOrderBooksByPriceSimulation();
            sendResponse(exchange, 200, "Get all books ordered by price.");
        };
    }

    @EndPoint(url = "getTotalAmountOfBooks", method = HttpMethod.GET)
    public HttpHandler runGetTotalBooksFromStoreSimulation() {
        return exchange -> {
            simulationService.runGetTotalBooksFromStoreSimulation();
            sendResponse(exchange, 200, "Get total amount of books.");
        };
    }

    @EndPoint(url = "getTotalBookPrice", method = HttpMethod.GET)
    public HttpHandler runGetTotalBookPriceFromBookStoreSimulation() {
        return exchange -> {
            simulationService.runGetTotalBookPriceFromBookStoreSimulation();
            sendResponse(exchange, 200, "Get total price of all books together.");
        };
    }

    @EndPoint(url = "getAverageBookPrice", method = HttpMethod.GET)
    public HttpHandler runGetAverageBookPriceFromBookStoreSimulation() {
        return exchange -> {
            simulationService.runGetAverageBookPriceFromBookStoreSimulation();
            sendResponse(exchange, 200, "Get average price of a book.");
        };
    }

    @EndPoint(url = "sellBooks", method = HttpMethod.GET)
    public HttpHandler runSellBookSimulation() {
        return exchange -> {
            simulationService.runSellBookSimulation();
            sendResponse(exchange, 200, "Sell books.");
        };
    }

    @EndPoint(url = "getTotalRevenue", method = HttpMethod.GET)
    public HttpHandler runTotalRevenueSimulation() {
        return exchange -> {
            simulationService.runTotalRevenueSimulation();
            sendResponse(exchange, 200, "Get total revenue.");
        };
    }

    @EndPoint(url = "getTotalBooksSold", method = HttpMethod.GET)
    public HttpHandler runTotalBooksSoldSimulation() {
        return exchange -> {
            simulationService.runTotalBooksSoldSimulation();
            sendResponse(exchange, 200, "get total books sold.");
        };
    }

    @EndPoint(url = "getAllBooksOrderedByRating", method = HttpMethod.GET)
    public HttpHandler runBooksByRatingRangeSimulation() {
        return exchange -> {
            simulationService.runBooksByRatingRangeSimulation();
            sendResponse(exchange, 200, "Get all books ordered by rating.");
        };
    }
}
