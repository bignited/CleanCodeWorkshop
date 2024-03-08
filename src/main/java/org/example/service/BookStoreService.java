package org.example.service;

import org.example.model.Book;
import org.example.model.BookStore;
import org.example.repository.BookStoreRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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

    public double calculateTotalPrice() {
        List<Book> bookList = getBookListFromBookStore();
        double total = 0;
        for (Book book : bookList) {
            total += book.getPrice();
        }
        return total;
    }
}
