package org.example.service;

import org.example.exception.BookStoreException;
import org.example.model.Book;
import org.example.model.BookStore;
import org.example.repository.BookStoreRepository;

import java.util.*;

public class BookStoreService {
    private final BookStoreRepository bookStoreRepository = new BookStoreRepository();

    public void createBookStore() {
        bookStoreRepository.createBookStore();
    }

    public void addBooks(List<Book> bookList) {
        BookStore bookStore = bookStoreRepository.getBookStore();
        bookStore.getBookList().addAll(bookList);
    }

    public List<Book> getBooksFromBookStoreByTitle(String bookTitleToFind) {
        LoggingService.logInfo(String.format("Looking for books by title: %s.", bookTitleToFind), this.getClass());
        List<Book> bookList = getBookListFromBookStore();
        if (bookList.isEmpty()) {
            LoggingService.logInfo(String.format("No books found with title: %s.", bookTitleToFind), this.getClass());
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
                LoggingService.logInfo(String.format("Book found with title: %s.", bookTitleToFind), this.getClass());
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

    public List<Book> getTopRatedBooksFromBookStore(int amountOfBooks) {
        List<Book> sortedBookList = sortBooksByRatingDesc();
        return sortedBookList.subList(0, amountOfBooks);
    }

    private List<Book> sortBooksByRatingDesc() {
        List<Book> bookList = new ArrayList<>(getBookListFromBookStore());
        bookList.sort(Comparator.comparingDouble(Book::getRating).reversed());
        return bookList;
    }

    public List<Book> sortBooksFromBookStoreByPrice() {
        List<Book> bookList = new ArrayList<>(getBookListFromBookStore());
        bookList.sort(Comparator.comparingDouble(Book::getPrice));
        return bookList;
    }

    public int getTotalBooksFromBookStore() {
        List<Book> bookList = getBookListFromBookStore();
        return bookList.size();
    }

    public double calculateTotalBookPrice() {
        List<Book> bookList = getBookListFromBookStore();
        double total = 0;
        for (Book book : bookList) {
            total += book.getPrice();
        }
        return total;
    }

    public double calculateAverageBookPriceInBookStore() {
        int totalBooks = getTotalBooksFromBookStore();
        double totalBookPrice = calculateTotalBookPrice();
        return totalBookPrice / totalBooks;
    }

    public void sellBooksByTitleAndAuthorName(HashMap<String, String> bookTitleAuthorMap) {
        for (Map.Entry<String, String> bookTitleAuthorEntry : bookTitleAuthorMap.entrySet()) {
            String bookTitle = bookTitleAuthorEntry.getKey();
            String bookAuthor = bookTitleAuthorEntry.getValue();
            Book bookToSell = getBookByTitleAndAuthor(bookTitle, bookAuthor);
            sellBookFromBookStore(bookToSell);
        }
    }

    private Book getBookByTitleAndAuthor(String bookTitle, String bookAuthor) {
        List<Book> bookList = getBookListFromBookStore();
        Book book = new Book(bookTitle, bookAuthor, 0.0);
        if (bookList.contains(book)) {
            return book;
        } else
            throw new BookStoreException(String.format("Book with title: %s, by: %s cannot be found in the bookstore.",
                    bookTitle, bookAuthor));
    }

    public void sellBookFromBookStore(Book bookToSell) {
        double totalSellPrice = 0;
        int amountOfBooksSold = 0;
        if (sellBook(bookToSell) > 0) {
            amountOfBooksSold++;
            totalSellPrice += bookToSell.getPrice();
        }
        BookStore bookStore = bookStoreRepository.getBookStore();
        bookStore.setTotalBooksSold(bookStore.getTotalBooksSold() + amountOfBooksSold);
        bookStore.setTotalRevenue(bookStore.getTotalRevenue() + totalSellPrice);
    }

    private double sellBook(Book bookToSell) {
        List<Book> bookList = getBookListFromBookStore();
        for (Book book : bookList) {
            if (book.equals(bookToSell)) {
                bookList.remove(book);
                book.increaseCopiesSold(1);
                return book.getPrice();
            }
        }
        return 0;
    }

    public double getBookStoreTotalRevenue() {
        return bookStoreRepository.getBookStore().getTotalRevenue();
    }

    public int getTotalBooksSold() {
        return bookStoreRepository.getBookStore().getTotalBooksSold();
    }

    public void logAllBookStoreBooks() {
        List<Book> bookList = getBookListFromBookStore();
        for (Book book : bookList) {
            book.logBookInfo();
        }
    }

    public List<Book> getBooksByRatingRange(double minRating, double maxRating) {
        List<Book> bookList = getBookListFromBookStore();
        List<Book> filteredBookList = new LinkedList<>();
        for (Book book : bookList) {
            if (isBookRatingInRange(book.getRating(), minRating, maxRating)) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

    private boolean isBookRatingInRange(double bookRating, double minRating, double maxRating) {
        return bookRating >= minRating && bookRating <= maxRating;
    }
}