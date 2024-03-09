package org.example.service;

import org.example.model.Book;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.HashMap;
import java.util.List;

public class SimulationService {
    private final BookService bookService = new BookService();
    private final BookStoreService bookStoreService = new BookStoreService();
    private final BookGeneratorService bookGeneratorService = new BookGeneratorService();

    public void runSimulation() {
        runBookStoreDataSetupSimulation();
        runFilterBooksSimulation();
        runUpdateRemoveBookSimulation();
        runOrderBooksByPriceSimulation();
        runGetTotalBooksFromStoreSimulation();
        runBookListCalculationsSimulation();
        runSellBookSimulation();
        runTotalRevenueAndTotalSoldSimulation();
        runBooksByRatingRangeSimulation();
        runBooksSortedByPriceSimulation();
    }

    private void runBookStoreDataSetupSimulation() {
        List<Book> bookList = bookGeneratorService.generateBooks();
        bookStoreService.addBooks(bookList);
    }

    private void runFilterBooksSimulation() {
        List<Book> filteredBookList = bookStoreService.getBooksFromBookStoreByTitle("Book1");
        List<Book> topRatedBookList = bookStoreService.getTopRatedBooksFromBookStore(1);
        bookService.logBookInfo(filteredBookList);
        bookService.logBookInfo(topRatedBookList);
    }

    private void runUpdateRemoveBookSimulation() {
        UpdateBookDetailsValueObject updateBookDetailsValueObject = new UpdateBookDetailsValueObject(
                "NewBook1", "NewAuthor1", 11.99);
        bookService.updateBookDetails("Book1", updateBookDetailsValueObject);
        bookService.removeBookByTitle("Book1");
    }

    private void runOrderBooksByPriceSimulation() {
        List<Book> orderedByPriceBookList = bookStoreService.sortBooksFromBookStoreByPrice();
        bookService.logBookInfo(orderedByPriceBookList);
    }

    private void runGetTotalBooksFromStoreSimulation() {
        int totalBooksInBookStore = bookStoreService.getTotalBooksFromBookStore();
        LoggingService.logInfo(String.format("Total number of books: %d", totalBooksInBookStore), this.getClass());
    }

    private void runBookListCalculationsSimulation() {
        double totalPriceOfBooksInBookStore = bookStoreService.calculateTotalBookPrice();
        LoggingService.logInfo(String.format("Total price of all books: %f.2", totalPriceOfBooksInBookStore), this.getClass());
        double averageBookPriceInBookStore = bookStoreService.calculateAverageBookPriceInBookStore();
        LoggingService.logInfo(String.format("Average price of books: %f.2", averageBookPriceInBookStore), this.getClass());
    }

    private void runSellBookSimulation() {
        HashMap<String, String> bookTitleAuthorMap = new HashMap<>();
        bookTitleAuthorMap.put("Book2", "Author2");
        bookStoreService.sellBooksByTitleAndAuthorName(bookTitleAuthorMap);
    }

    private void runTotalRevenueAndTotalSoldSimulation() {
        double totalBookStoreRevenue = bookStoreService.getBookStoreTotalRevenue();
        LoggingService.logInfo(String.format("Total revenue of bookstore: %f.2", totalBookStoreRevenue), this.getClass());
        int totalAmountOfBooksSold = bookStoreService.getTotalBooksSold();
        LoggingService.logInfo(String.format("Total amount of books sold: %d", totalAmountOfBooksSold), this.getClass());
        bookStoreService.logAllBookStoreBooks();
    }

    private void runBooksByRatingRangeSimulation() {
        List<Book> booksByRatingRange = bookStoreService.getBooksByRatingRange(3, 5);
        bookService.logBookInfo(booksByRatingRange);
    }

    private void runBooksSortedByPriceSimulation() {
        List<Book> booksSortedByPrice = bookStoreService.getBooksSortedByPrice();
        bookService.logBookInfo(booksSortedByPrice);
    }
}