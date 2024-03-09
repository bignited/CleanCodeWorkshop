package org.example.repository;

import org.example.model.BookStore;

import java.util.ArrayList;
import java.util.List;

public class BookStoreRepository {
    private static final List<BookStore> bookStoreList = new ArrayList<>();

    public List<BookStore> getBookStoreList() {
        return bookStoreList;
    }
}
