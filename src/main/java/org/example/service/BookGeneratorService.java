package org.example.service;

import org.example.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookGeneratorService {
    private static final Logger LOGGER = Logger.getLogger(BookGeneratorService.class.getName());
    private final int AMOUNT_OF_BOOKS = 10;

    public List<Book> generateBooks() {
        LOGGER.info("Generating new books.");
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_BOOKS; i++) {
            Book newBook = new Book("Title" + i, "Author" + i, 10.0 + i);
            bookList.add(newBook);
        }
        return bookList;
    }
}
