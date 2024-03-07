package org.example.repository;

import org.example.model.BookStore;

public class BookStoreRepository {
    private static BookStore bookStore;

    public void createBookStore() {
        bookStore = new BookStore();
    }

    public BookStore getBookStore() {
        return bookStore;
    }
}
