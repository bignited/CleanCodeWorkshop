package org.example.service;

import org.example.model.Book;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.HashMap;
import java.util.List;

public class SimulationService {

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

        double totalPriceOfBooksInBookStore = bookStoreService.calculateTotalBookPrice();
        LoggingService.logInfo(String.format("Total price of all books: %f.2", totalPriceOfBooksInBookStore), this.getClass());
        double averageBookPriceInBookStore = bookStoreService.calculateAverageBookPriceInBookStore();
        LoggingService.logInfo(String.format("Average price of books: %f.2", averageBookPriceInBookStore), this.getClass());

        HashMap<String, String> bookTitleAuthorMap = new HashMap<>();
        bookTitleAuthorMap.put("Book2", "Author2");
        bookStoreService.sellBooksByTitleAndAuthorName(bookTitleAuthorMap);

        double totalBookStoreRevenue = bookStoreService.getBookStoreTotalRevenue();
        LoggingService.logInfo(String.format("Total revenue of bookstore: %f.2", totalBookStoreRevenue), this.getClass());

        int totalAmountOfBooksSold = bookStoreService.getTotalBooksSold();
        LoggingService.logInfo(String.format("Total amount of books sold: %d", totalAmountOfBooksSold), this.getClass());

        bookStoreService.logAllBookStoreBooks();

        List<Book> booksByRatingRange = bookStoreService.getBooksByRatingRange(3, 5);
        bookService.logBookInfo(booksByRatingRange);

        List<Book> booksSortedByPrice = store.getBooksSortedByPrice();
        for (Book book : booksSortedByPrice) {
            System.out.println(book.getTitle() + " - " + book.getPrice());
        }

        store.processBooks();
        store.displayBooks(true);
        store.updateBookPrice("book1", 50);
    }
}