package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        BookStore store = new BookStore();

        Gson gson = new Gson();
        try {
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            List<Book> books = gson.fromJson(new FileReader("src/main/java/org/example/books.json"), bookListType);
            for (Book book : books) {
                store.addBook(book);
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        Book searchedBook = store.getBookByTitle("Book1");
        if (searchedBook != null) {
            LOGGER.info("Found book: " + searchedBook);
        } else {
            LOGGER.info("Book not found");
        }

        List<Book> topRatedBooks = store.getAmountOfTopRatedBooks(1);
        for (Book book : topRatedBooks) {
            System.out.println(book.getTitle() + " - " + book.getRating());
        }

        Book book1 = store.getBookByTitle("Book1");
        book1.updateBookDetails( "NewBook1", "NewAuthor1", 11.99);
        store.removeBook("Book1");
        store.sortBooksByPrice();
        store.displayBooks();
        LOGGER.info("Total number of books: " + store.getTotalBooks());
        LOGGER.info("Total price of all books: " + store.calculateTotalPriceOfAllBooks());
        LOGGER.info("Average price of books: " + store.calculateAveragePriceOfAllBooks());
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
        store.displayBooksSortedByPriceOrRating(true);
        store.updateBookPrice("book1", 50)                                                                                                                                                                                                                                                                                          ;
    }
}