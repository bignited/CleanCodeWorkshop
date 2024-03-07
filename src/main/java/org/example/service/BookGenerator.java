package org.example.service;

import org.example.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookGenerator {
    private static final int AMOUNT_OF_BOOKS = 10;

    public List<Book> generateBooks() {
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_BOOKS; i++) {
            Book newBook = new Book("Book" + i, "Author" + i, 10.0 + i);
            bookList.add(newBook);
        }
        return bookList;
    }
}
