package org.example.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

public interface Controller {
    Logger LOGGER = Logger.getLogger(Controller.class.getName());

    default void sendResponse(HttpExchange exchange, int statusCode, int responseLength, URI requestUri) {
        try {
            exchange.sendResponseHeaders(statusCode, responseLength);
        } catch (IOException e) {
            String logMessage = String.format("Could not send response to client for API endpoint: %s." +
                    "\nOrginal exception message: %s", requestUri.toString(), e.getMessage());
            LOGGER.severe(logMessage);
        }
        exchange.close();
    }
}
