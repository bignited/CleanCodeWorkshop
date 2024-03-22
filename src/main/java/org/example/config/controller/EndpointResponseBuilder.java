package org.example.config.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.service.LoggingService;

import java.io.IOException;
import java.io.OutputStream;

public class EndpointResponseBuilder {

    public static <T> void buildGetResponse(HttpExchange exchange, T responseBodyObject, int statusCode) {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Status", String.valueOf(statusCode));
        sendResponseHeaders(exchange, statusCode);
        buildResponseBody(exchange, responseBodyObject);
    }

    private static void sendResponseHeaders(HttpExchange exchange, int statusCode) {
        try {
            exchange.sendResponseHeaders(statusCode, 0);
        } catch (IOException e) {
            LoggingService.logInfo("Error sending response headers: " + e.getMessage(),
                    EndpointResponseBuilder.class);
        }
    }

    private static <T> void buildResponseBody(HttpExchange exchange, T responseBodyObject) {
        byte[] responseInBytes = writeResponseObjectToBytes(responseBodyObject);
        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(responseInBytes, 0, responseInBytes.length);
        } catch (IOException e) {
            LoggingService.logInfo("Error writing response body: " + e.getMessage(),
                    EndpointResponseBuilder.class);
        }
    }

    private static <T> byte[] writeResponseObjectToBytes(T responseBodyObject) {
        return responseBodyObject.toString().getBytes();
    }
}
