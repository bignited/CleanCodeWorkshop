package org.example;

import org.example.model.Book;
import org.example.model.BookStore;
import org.example.service.BookGenerator;

import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        BookGenerator bookGenerator = new BookGenerator();
        BookStore bookStore = new BookStore();

        List<Book> bookList = bookGenerator.generateBooks();
        bookStore.addBooks(bookList);

        List<Book> foundBooks = bookStore.getBooksByTitle("Book1");
        List<Book> topRatedBooks = bookStore.getTopRatedBooks(1);
        bookStore.printBooks(foundBooks);

        List<Book> topRatedBooks = bookStore.getTopRatedBooks(1);
        for (Book book : topRatedBooks) {
            System.out.println(book.getTitle() + " - " + book.getAverageRating());
        }

        bookStore.updateBookDetails("Book1", "NewBook1", "NewAuthor1", 11.99);
        bookStore.removeBook("Book1");
        bookStore.sortBooksByPrice();
        bookStore.displayBooks();
        LOGGER.info("Total number of books: " + bookStore.getTotalBooks());
        LOGGER.info("Total price of all books: " + bookStore.getTotalPrice());
        LOGGER.info("Average price of books: " + bookStore.calculateAveragePrice());
        bookStore.sellBook("Book2");
        LOGGER.info("Total revenue: " + bookStore.getTotalRevenue());
        LOGGER.info("Number of books sold: " + bookStore.getAmountOfBooksSold());
        bookStore.displayBooks();

        List<Book> booksByRatingRange = bookStore.getBooksByRatingRange(3, 5);
        for (Book book : booksByRatingRange) {
            System.out.println(book.getTitle() + " - " + book.getAverageRating());
        }

        List<Book> booksSortedByPrice = bookStore.getBooksSortedByPrice();
        for (Book book : booksSortedByPrice) {
            System.out.println(book.getTitle() + " - " + book.getPrice());
        }

        bookStore.processBooks();
        bookStore.displayBooks(true);
        bookStore.updateBookPrice("book1", 50)                                                                                                                                                                                                                                                                                          ;
    }
}