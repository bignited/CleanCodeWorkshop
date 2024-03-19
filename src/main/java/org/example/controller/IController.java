package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.service.LoggingService;

import java.io.IOException;
import java.net.URI;

public interface IController {

    default void sendResponse(HttpExchange exchange, int statusCode, int responseLength, URI requestUri) {
        try {
            exchange.sendResponseHeaders(statusCode, responseLength);
        } catch (IOException e) {
            String logMessage = String.format("Could not send response to client for API endpoint: %s." +
                    "\nOrginal exception message: %s", requestUri.toString(), e.getMessage());
            LoggingService.logInfo(logMessage, this.getClass());
        }
        exchange.close();
    }
}
