package org.example.service;

import org.example.model.Book;
import org.example.model.BookStore;
import org.example.repository.BookStoreRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class BookStoreService {
    private static final Logger LOGGER = Logger.getLogger(BookStoreService.class.getName());
    private final BookStoreRepository bookStoreRepository = new BookStoreRepository();

    public void createBookStore() {
        bookStoreRepository.createBookStore();
    }

    public void addBooks(List<Book> bookList) {
        BookStore bookStore = bookStoreRepository.getBookStore();
        bookStore.getBookList().addAll(bookList);
    }

    public List<Book> getBooksFromBookStoreByTitle(String bookTitleToFind) {
        LOGGER.info(String.format("Looking for books by title: %s.", bookTitleToFind));
        List<Book> bookList = getBookListFromBookStore();
        if (bookList.isEmpty()) {
            LOGGER.info(String.format("No books found with title: %s.", bookTitleToFind));
        }
        return filterBooksByTitle(bookList, bookTitleToFind);
    }

    private List<Book> getBookListFromBookStore() {
        BookStore bookStore = bookStoreRepository.getBookStore();
        return bookStore.getBookList();
    }

    private List<Book> filterBooksByTitle(List<Book> unfilteredBookList, String bookTitleToFind) {
        List<Book> filteredBookList = new LinkedList<>();
        for (Book book : unfilteredBookList) {
            if (book.getTitle().equalsIgnoreCase(bookTitleToFind)) {
                LOGGER.info(String.format("Book found with title: %s.", bookTitleToFind));
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }
}
