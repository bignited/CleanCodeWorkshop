package org.example.service;

import org.example.exception.BookStoreException;
import org.example.model.Book;
import org.example.model.BookStore;
import org.example.repository.BookStoreRepository;

import java.util.HashMap;
import java.util.List;

public class BookStoreService {
    private final BookService bookService = new BookService();
    private final BookStoreRepository bookStoreRepository = new BookStoreRepository();

    public BookStore addNewBookStore(String bookStoreName) {
        BookStore bookStore = new BookStore(bookStoreName);
        bookStoreRepository.getBookStoreList().add(bookStore);
        LoggingService.logInfo("New bookstore added with name: " + bookStoreName, this.getClass());
        return bookStore;
    }

    public void addBooksToBookStoreById(int bookStoreId, List<Book> bookListToAdd) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        bookList.addAll(bookListToAdd);
    }

    private BookStore getBookStoreById(int bookStoreId) {
        List<BookStore> bookStoreList = bookStoreRepository.getBookStoreList();
        for (BookStore bookStore : bookStoreList) {
            if (bookStore.getId() == bookStoreId) {
                return bookStore;
            }
        }
        throw new BookStoreException(String.format("Could not find bookstore with ID: %d.", bookStoreId));
    }

    public List<Book> getAllBooksFromBookStoreByTitle(int bookStoreId, String bookTitle) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        return bookService.filterBooksByTitle(bookList, bookTitle);
    }

    public List<Book> getAllBooksFromBookStoreByBookStoreId(int bookStoreId) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        return bookStore.getBookList();
    }

    public List<Book> getTopRatedBooksFromBookStore(int bookStoreId, int amountOfTopRatedBooks) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        return bookService.getTopRatedBooksFromBookList(bookStore.getBookList(), amountOfTopRatedBooks);
    }

    public int getTotalAmountOfBooksFromBookStore(int bookStoreId) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        return bookList.size();
    }

    public double getTotalPriceOfAllBooksFromBookStore(int bookStoreId) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        return bookService.getTotalPriceFromBooks(bookList);
    }

    public double getAverageBookPriceFromBookStore(int bookStoreId) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        return bookService.getAveragePriceFromBooks(bookList);
    }

    public void sellBooksByTitleAndAuthorNameFromBookStore(int bookStoreId, HashMap<String, String> bookTitleAuthorMap) {
        List<Book> bookListToSell = bookService.getAllBooksByTitleAndAuthorMap(bookTitleAuthorMap);
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        sellBooksFromBookStore(bookStoreId, bookListToSell);
        bookList.removeAll(bookListToSell);
    }

    private void sellBooksFromBookStore(int bookStoreId, List<Book> bookListToSell) {
        bookService.sellBookList(bookListToSell);
        double totalPriceOfSoldBooks = bookService.getTotalPriceFromBooks(bookListToSell);
        increaseTotalRevenueFromBookStore(bookStoreId, totalPriceOfSoldBooks);
        increaseAmountOfBooksSold(bookStoreId, bookListToSell.size());
    }

    private void increaseTotalRevenueFromBookStore(int bookStoreId, double amount) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        double currentTotalRevenue = bookStore.getTotalRevenue();
        double newTotalRevenue = currentTotalRevenue + amount;
        bookStore.setTotalRevenue(newTotalRevenue);
    }

    private void increaseAmountOfBooksSold(int bookStoreId, int size) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        int currentTotalSoldBooks = bookStore.getTotalBooksSold();
        int newTotalSoldBooks = currentTotalSoldBooks + size;
        bookStore.setTotalBooksSold(newTotalSoldBooks);
    }

    public double getBookStoreTotalRevenue(int bookStoreId) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        return bookStore.getTotalRevenue();
    }

    public int getBookStoreTotalBooksSold(int bookStoreId) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        return bookStore.getTotalBooksSold();
    }

    public List<Book> getAllBooksFromBookStoreByRatingRange(int bookStoreId, int minRatingRange, int maxRatingRange) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        return bookService.filterBookListByRatingRange(bookList, minRatingRange, maxRatingRange);
    }

    public List<Book> getAllBooksFromBookStoreOrderByPriceDesc(int bookStoreId) {
        List<Book> bookList = getAllBooksFromBookStoreByBookStoreId(bookStoreId);
        return bookService.orderBooksByPriceDesc(bookList);
    }
}