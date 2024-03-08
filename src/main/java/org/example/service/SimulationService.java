package org.example.service;

import org.example.Main;
import org.example.model.Book;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.List;
import java.util.logging.Logger;

public class SimulationService {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public void runSimulation() {
        BookGeneratorService bookGeneratorService = new BookGeneratorService();
        BookStoreService bookStoreService = new BookStoreService();
        BookService bookService = new BookService();

        List<Book> bookList = bookGeneratorService.generateBooks();
        bookStoreService.createBookStore();
        bookStoreService.addBooks(bookList);
        List<Book> filteredBookList = bookStoreService.getBooksFromBookStoreByTitle("Book1");
        List<Book> topRatedBookList = bookStoreService.getTopRatedBooksFromBookStore(1);
        bookService.logBookInfo(topRatedBookList);

        UpdateBookDetailsValueObject updateBookDetailsValueObject = new UpdateBookDetailsValueObject(
                "NewBook1", "NewAuthor1", 11.99);
        bookService.updateBookDetails("Book1", updateBookDetailsValueObject);
        bookService.removeBookByTitle("Book1");

        List<Book> orderedByPriceBookList = bookStoreService.sortBooksFromBookStoreByPrice();
        bookService.logBookInfo(orderedByPriceBookList);

        int totalBooksInBookStore = bookStoreService.getTotalBooksFromBookStore();
        LoggingService.logInfo(String.format("Total number of books: %d", totalBooksInBookStore), this.getClass());

        double totalPriceOfBooks = bookStoreService.calculateTotalPrice();
        LoggingService.logInfo(String.format("Total price of all books: %f.2", totalPriceOfBooks), this.getClass());
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