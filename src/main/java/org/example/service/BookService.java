package org.example.service;

import org.example.model.Book;

import java.util.List;

public class BookService {

    public void logBookInfo(List<Book> bookList) {
        for (Book book: bookList) {
            book.logInfo();
        }
    }
}
