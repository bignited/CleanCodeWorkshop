package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.example.config.controller.EndpointResponseBuilder;
import org.example.service.LoggingService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public interface IController {
    default <T> void sendResponse(HttpExchange exchange, int statusCode, T responseBody) {
        EndpointResponseBuilder.buildGetResponse(exchange, responseBody, statusCode);
        exchange.close();
    }

    default <T> T convertRequestBodyIntoObject(InputStream requestBody, Class<T> returnType) {
        byte[] requestBodyBytes = readBytesFromRequestBody(requestBody);
        String requestBodyString = new String(requestBodyBytes, StandardCharsets.UTF_8);
        return mapJsonStringToObject(requestBodyString, returnType);
    }

    private static byte[] readBytesFromRequestBody(InputStream requestBody) {
        try {
            return requestBody.readAllBytes();
        } catch (IOException e) {
            LoggingService.logInfo("Error reading request body: " + e.getMessage(), IController.class);
        }
        return new byte[0];
    }

    private <T> T mapJsonStringToObject(String jsonString, Class<T> returnType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, returnType);
        } catch (JsonProcessingException e) {
            LoggingService.logInfo("Error mapping JSON string to object: " + e.getMessage(), IController.class);
            return null;
        }
    }
}
