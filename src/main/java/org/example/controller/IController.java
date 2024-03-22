package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.config.controller.EndpointResponseBuilder;

public interface IController {
    default <T> void sendResponse(HttpExchange exchange, int statusCode, T responseBody) {
        EndpointResponseBuilder.buildGetResponse(exchange, responseBody, statusCode);
        exchange.close();
    }
}
