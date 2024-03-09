package org.example.service;

import org.example.model.Book;
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
        runBookListCalculationsSimulation();
        runSellBookSimulation();
        runTotalRevenueAndTotalSoldSimulation();
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

    private void logInfoOfBookStoreBookList(int bookStoreId) {
        List<Book> bookList = bookStoreService.getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        bookService.logBookInfo(bookList);
    }
}