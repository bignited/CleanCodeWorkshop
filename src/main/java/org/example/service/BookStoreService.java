package org.example.service;

import org.example.exception.BookStoreException;
import org.example.model.Book;
import org.example.model.BookStore;
import org.example.repository.BookStoreRepository;

import java.util.*;

public class BookStoreService {
    private final BookService bookService = new BookService();
    private final BookStoreRepository bookStoreRepository = new BookStoreRepository();

    private List<Book> getBookListFromBookStore() {
        BookStore bookStore = bookStoreRepository. ();
        return bookStore.getBookList();
    }


    public List<Book> getTopRatedBooksFromBookStore(int bookStoreId, int amountOfTopRatedBooks) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        return bookService.getTopRatedBooksFromBookList(bookStore.getBookList(), amountOfTopRatedBooks);
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
                book.setNumberOfCopiesSold(book.getNumberOfCopiesSold() + 1);
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

    public List<Book> getBooksSortedByPrice() {
        List<Book> bookList = new ArrayList<>(getBookListFromBookStore());
        bookList.sort(Comparator.comparingDouble(Book::getPrice).reversed());
        return bookList;
    }

    public BookStore addNewBookStore(String bookStoreName) {
        BookStore bookStore = new BookStore(bookStoreName);
        bookStoreRepository.getBookStoreList().add(bookStore);
        return bookStore;
    }

    public void addBooksToBookStoreById(int bookStoreId, List<Book> bookList) {
        BookStore bookStore = getBookStoreById(bookStoreId);
        bookStore.getBookList().addAll(bookList);
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
        BookStore bookStore = getBookStoreById(bookStoreId);
        return bookService.filterBooksByTitle(bookStore.getBookList(), bookTitle);
    }
}