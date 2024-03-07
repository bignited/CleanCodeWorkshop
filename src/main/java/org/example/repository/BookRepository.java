package org.example.repository;

import org.example.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final List<Book> bookList = new ArrayList<>();

    public List<Book> getBookList() {
        return bookList;
    }
}
