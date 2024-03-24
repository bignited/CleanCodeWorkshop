package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.annotation.API;
import org.example.annotation.EndPoint;
import org.example.model.controller.HttpMethod;
import org.example.model.controller.HttpStatusCode;
import org.example.model.dto.AddBookStoreDto;
import org.example.service.BookStoreService;

@API(endpoint = "/bookstore/")
public class BookStoreController implements IController {
    private final BookStoreService bookStoreService;

    public BookStoreController() {
        this.bookStoreService = new BookStoreService();
    }

    @EndPoint(url = "add", method = HttpMethod.POST)
    public HttpHandler addBookStore() {
        return exchange -> {
            if (exchange.getRequestMethod().equals("POST")) {
                handleAddBookStoreRequest(exchange);
            } else {
                HttpStatusCode statusCode = HttpStatusCode.NOT_ALLOWED;
                sendResponse(exchange, statusCode.statusCode, statusCode.statusCodeMessage);
            }
        };
    }

    private void handleAddBookStoreRequest(HttpExchange exchange) {
        AddBookStoreDto addBookStoreDto = convertRequestBodyIntoObject(exchange.getRequestBody(),
                AddBookStoreDto.class);
        bookStoreService.addNewBookStore(addBookStoreDto.getBookStoreName());
        HttpStatusCode statusCode = HttpStatusCode.CREATED;
        sendResponse(exchange, statusCode.statusCode, statusCode.statusCodeMessage);
    }
}
