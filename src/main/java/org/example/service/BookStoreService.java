package org.example.service;

import org.example.model.Book;
import org.example.model.BookStore;
import org.example.repository.BookStoreRepository;

import java.util.List;

public class BookStoreService {
    private final BookStoreRepository bookStoreRepository = new BookStoreRepository();

    public void createBookStore() {
        bookStoreRepository.createBookStore();
    }

    public void addBooks(List<Book> bookList) {
        BookStore bookStore = bookStoreRepository.getBookStore();
        bookStore.getBookList().addAll(bookList);
    }
}
