package org.example.service;

import org.example.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookGeneratorService {
    private final int BASE_PRICE = 10;

    public List<Book> generateBooks(int amoutOfBooks) {
        LoggingService.logInfo("Generating new books.", this.getClass());
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < amoutOfBooks; i++) {
            Book newBook = new Book("Book" + i, "Author" + i, BASE_PRICE + i);
            bookList.add(newBook);
        }
        return bookList;
    }
}
