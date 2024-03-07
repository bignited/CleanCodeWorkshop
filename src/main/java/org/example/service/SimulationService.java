package org.example.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Main;
import org.example.model.Book;
import org.example.model.BookStore;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

public class SimulationService {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public void runSimulation() {
        BookGeneratorService bookGeneratorService = new BookGeneratorService();
        BookStoreService bookStoreService = new BookStoreService();

        bookStoreService.createBookStore();
        List<Book> bookList = bookGeneratorService.generateBooks();
        bookStoreService.addBooks(bookList);


        Book searchedBook = store.searchBookByTitle("Book1");
        if (searchedBook != null) {
            LOGGER.info("Found book: " + searchedBook);
        } else {
            LOGGER.info("Book not found");
        }

        List<Book> topRatedBooks = store.getTopRatedBooks(1);
        for (Book book : topRatedBooks) {
            System.out.println(book.getTitle() + " - " + book.getRating());
        }

        store.updateBookDetails("Book1", "NewBook1", "NewAuthor1", 11.99);
        store.removeBook("Book1");
        store.sortBooksByPrice();
        store.displayBooks();
        LOGGER.info("Total number of books: " + store.getTotalBooks());
        LOGGER.info("Total price of all books: " + store.getTotalPrice());
        LOGGER.info("Average price of books: " + store.calculateAveragePrice());
        store.sellBook("Book2");
        LOGGER.info("Total revenue: " + store.getTotalRevenue());
        LOGGER.info("Number of books sold: " + store.getBooksSold());
        store.displayBooks();

        List<Book> booksByRatingRange = store.getBooksByRatingRange(3, 5);
        for (Book book : booksByRatingRange) {
            System.out.println(book.getTitle() + " - " + book.getRating());
        }

        List<Book> booksSortedByPrice = store.getBooksSortedByPrice();
        for (Book book : booksSortedByPrice) {
            System.out.println(book.getTitle() + " - " + book.getPrice());
        }

        store.processBooks();
        store.displayBooks(true);
        store.updateBookPrice("book1", 50);
    }
}