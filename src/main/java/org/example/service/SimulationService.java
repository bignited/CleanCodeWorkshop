package org.example.service;

import org.example.model.Book;
import org.example.model.BookDetails;
import org.example.model.BookStore;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.HashMap;
import java.util.List;

public class SimulationService {
    private final BookService bookService = new BookService();
    private final BookStoreService bookStoreService = new BookStoreService();
    private final BookGeneratorService bookGeneratorService = new BookGeneratorService();

    private static int BOOK_STORE_ID_1;
    private static int BOOK_STORE_ID_2;

    public void runSimulation() {
        runBookStoreCreationSimulation();
        runBookStoreAddBooksSimulation();
        runFilterBooksByTitleSimulation();
        runTopRatedBooksSimulation();
        runUpdateBookSimulation();
        runRemoveBookSimulation();
        runOrderBooksByPriceSimulation();
        runGetTotalBooksFromStoreSimulation();
        runGetTotalBookPriceFromBookStoreSimulation();
        runGetAverageBookPriceFromBookStoreSimulation();
        runSellBookSimulation();
        runTotalRevenueSimulation();
        runTotalBooksSoldSimulation();
        runBooksByRatingRangeSimulation();

        runBooksSortedByPriceSimulation();
    }

    private void runBookStoreCreationSimulation() {
        BookStore bookStore_1 = bookStoreService.addNewBookStore("BookStore_1");
        BookStore bookStore_2 = bookStoreService.addNewBookStore("BookStore_2");
        BOOK_STORE_ID_1 = bookStore_1.getId();
        BOOK_STORE_ID_2 = bookStore_2.getId();
    }

    private void runBookStoreAddBooksSimulation() {
        List<Book> bookList = bookGeneratorService.generateBooks();
        bookStoreService.addBooksToBookStoreById(BOOK_STORE_ID_1, bookList);
        bookStoreService.addBooksToBookStoreById(BOOK_STORE_ID_2, bookList);
        logInfoOfBookStoreBookList(BOOK_STORE_ID_1);
        logInfoOfBookStoreBookList(BOOK_STORE_ID_2);
    }

    private void runFilterBooksByTitleSimulation() {
        List<Book> bookListByTitle_1 = bookStoreService.getAllBooksFromBookStoreByTitle(BOOK_STORE_ID_1, "Book1");
        List<Book> bookListByTitle_2 = bookStoreService.getAllBooksFromBookStoreByTitle(BOOK_STORE_ID_2, "Book2");
        bookService.logBookInfo(bookListByTitle_1);
        bookService.logBookInfo(bookListByTitle_2);
    }

    private void runTopRatedBooksSimulation() {
        int amountOfTopRatedBooks = 3;
        List<Book> topRatedBookList_1 = bookStoreService.getTopRatedBooksFromBookStore(BOOK_STORE_ID_1, amountOfTopRatedBooks);
        List<Book> topRatedBookList_2 = bookStoreService.getTopRatedBooksFromBookStore(BOOK_STORE_ID_2, amountOfTopRatedBooks);
        bookService.logBookInfo(topRatedBookList_1);
        bookService.logBookInfo(topRatedBookList_2);
    }

    private void runUpdateBookSimulation() {
        updateBookSimulation(BOOK_STORE_ID_1, "NewBookTitle_1");
        updateBookSimulation(BOOK_STORE_ID_2, "NewBookTitle_2");
        logInfoOfBookStoreBookList(BOOK_STORE_ID_1);
        logInfoOfBookStoreBookList(BOOK_STORE_ID_2);
    }

    private void updateBookSimulation(int bookStoreId, String newBookTitle) {
        List<Book> bookList = bookStoreService.getAllBooksFromBookStoreByTitle(bookStoreId, newBookTitle);
        Book bookToUpdate = bookList.get(0);
        UpdateBookDetailsValueObject updateBookDetailsValueObject = new UpdateBookDetailsValueObject(
                newBookTitle, bookToUpdate.getBookDetails().getAuthor(), bookToUpdate.getBookDetails().getPrice());
        bookService.updateBookDetailsByBookId(bookToUpdate.getId(), updateBookDetailsValueObject);
    }

    private void runRemoveBookSimulation() {
        removeBookSimulation(BOOK_STORE_ID_1, "NewBookTitle_1");
        removeBookSimulation(BOOK_STORE_ID_2, "NewBookTitle_2");
        logInfoOfBookStoreBookList(BOOK_STORE_ID_1);
        logInfoOfBookStoreBookList(BOOK_STORE_ID_2);
    }

    private void removeBookSimulation(int bookStoreId, String titleBookToRemove) {
        List<Book> bookList = bookStoreService.getAllBooksFromBookStoreByTitle(bookStoreId, titleBookToRemove);
        Book bookToRemove = bookList.get(0);
        String authorBookToRemove = bookToRemove.getBookDetails().getAuthor();
        bookService.removeBookByTitleAndAuthor(titleBookToRemove, authorBookToRemove);
    }

    private void runOrderBooksByPriceSimulation() {
        List<Book> bookListOrderedByPrice = bookService.getAllBooksOrderedByPrice();
        bookService.logBookInfo(bookListOrderedByPrice);
    }

    private void runGetTotalBooksFromStoreSimulation() {
        int totalBooksInBookStore_1 = bookStoreService.getTotalAmountOfBooksFromBookStore(BOOK_STORE_ID_1);
        int totalBooksInBookStore_2 = bookStoreService.getTotalAmountOfBooksFromBookStore(BOOK_STORE_ID_2);
        LoggingService.logInfo(String.format("Total number of books: %d", totalBooksInBookStore_1), this.getClass());
        LoggingService.logInfo(String.format("Total number of books: %d", totalBooksInBookStore_2), this.getClass());
    }

    private void runGetTotalBookPriceFromBookStoreSimulation() {
        double totalPriceOfBooksInBookStore_1 = bookStoreService.getTotalPriceOfAllBooksFromBookStore(BOOK_STORE_ID_1);
        double totalPriceOfBooksInBookStore_2 = bookStoreService.getTotalPriceOfAllBooksFromBookStore(BOOK_STORE_ID_2);
        LoggingService.logInfo(String.format("Total price of all books: %f.2", totalPriceOfBooksInBookStore_1), this.getClass());
        LoggingService.logInfo(String.format("Total price of all books: %f.2", totalPriceOfBooksInBookStore_2), this.getClass());
    }

    private void runGetAverageBookPriceFromBookStoreSimulation() {
        double averageBookPriceInBookStore_1 = bookStoreService.getAverageBookPriceFromBookStore(BOOK_STORE_ID_1);
        double averageBookPriceInBookStore_2 = bookStoreService.getAverageBookPriceFromBookStore(BOOK_STORE_ID_2);
        LoggingService.logInfo(String.format("Average price of books: %f.2", averageBookPriceInBookStore_1), this.getClass());
        LoggingService.logInfo(String.format("Average price of books: %f.2", averageBookPriceInBookStore_2), this.getClass());
    }

    private void runSellBookSimulation() {
        sellBookSimulation(BOOK_STORE_ID_1, "Book3");
        sellBookSimulation(BOOK_STORE_ID_1, "Book4");
        logInfoOfBookStoreBookList(BOOK_STORE_ID_1);
        logInfoOfBookStoreBookList(BOOK_STORE_ID_2);
    }

    private void sellBookSimulation(int bookStoreId, String titleBookToSell) {
        List<Book> bookList = bookStoreService.getAllBooksFromBookStoreByTitle(bookStoreId, titleBookToSell);
        Book bookToSell = bookList.get(0);
        BookDetails bookDetails = bookToSell.getBookDetails();
        HashMap<String, String> bookTitleAuthorMap = new HashMap<>();
        bookTitleAuthorMap.put(bookDetails.getTitle(), bookDetails.getAuthor());
        bookStoreService.sellBooksByTitleAndAuthorNameFromBookStore(bookStoreId, bookTitleAuthorMap);
    }

    private void runTotalRevenueSimulation() {
        double totalBookStoreRevenue_1 = bookStoreService.getBookStoreTotalRevenue(BOOK_STORE_ID_1);
        double totalBookStoreRevenue_2 = bookStoreService.getBookStoreTotalRevenue(BOOK_STORE_ID_2);
        LoggingService.logInfo(String.format("Total revenue of bookstore: %f.2", totalBookStoreRevenue_1), this.getClass());
        LoggingService.logInfo(String.format("Total revenue of bookstore: %f.2", totalBookStoreRevenue_2), this.getClass());
    }

    private void runTotalBooksSoldSimulation() {
        int totalAmountOfBooksSold_1 = bookStoreService.getBookStoreTotalBooksSold(BOOK_STORE_ID_1);
        int totalAmountOfBooksSold_2 = bookStoreService.getBookStoreTotalBooksSold(BOOK_STORE_ID_2);
        LoggingService.logInfo(String.format("Total amount of books sold: %d", totalAmountOfBooksSold_1), this.getClass());
        LoggingService.logInfo(String.format("Total amount of books sold: %d", totalAmountOfBooksSold_2), this.getClass());
    }

    private void runBooksByRatingRangeSimulation() {
        List<Book> booksByRatingRange_1 = bookStoreService.getAllBooksFromBookStoreByRatingRange(BOOK_STORE_ID_1, 3, 5);
        List<Book> booksByRatingRange_2 = bookStoreService.getAllBooksFromBookStoreByRatingRange(BOOK_STORE_ID_2, 1, 5);
        bookService.logBookInfo(booksByRatingRange_1);
        bookService.logBookInfo(booksByRatingRange_2);
    }

    private void runBooksSortedByPriceSimulation() {
        List<Book> booksSortedByPrice_1 = bookStoreService.getAllBooksFromBookStoreOrderByPriceDesc(BOOK_STORE_ID_1);
        List<Book> booksSortedByPrice_2 = bookStoreService.getAllBooksFromBookStoreOrderByPriceDesc(BOOK_STORE_ID_2);
        bookService.logBookInfo(booksSortedByPrice_1);
        bookService.logBookInfo(booksSortedByPrice_2);
    }

    private void logInfoOfBookStoreBookList(int bookStoreId) {
        List<Book> bookList = bookStoreService.getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        bookService.logBookInfo(bookList);
    }
}