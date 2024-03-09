package org.example.service;

import org.example.exception.BookException;
import org.example.model.Book;
import org.example.model.BookDetails;
import org.example.repository.BookRepository;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();

    public void logBookInfo(List<Book> bookList) {
        for (Book book : bookList) {
            logBookInfo(book);
        }
    }

    private void logBookInfo(Book book) {
        BookDetails bookDetails = book.getBookDetails();
        String logMessage = String.format("Book: Title:%s - Author:%s - Rating:%f.2 - Price:%f.2",
                bookDetails.getTitle(), bookDetails.getAuthor(), book.getRating(), bookDetails.getPrice());
        LoggingService.logInfo(logMessage, this.getClass());
    }

    public void updateBookDetails(String titleOfBookToUpdate, UpdateBookDetailsValueObject updateBookDetailsValueObject) {
        Book book = getBookByTitle(titleOfBookToUpdate);
        updateBookDetails(book, updateBookDetailsValueObject);
    }

    private Book getBookByTitle(String titleToFind) {
        List<Book> bookList = bookRepository.getBookList();
        for (Book book : bookList) {
            if (book.getTitle().equals(titleToFind)) {
                return book;
            }
        }
        throw new BookException(String.format("Book with title: %s cannot be found.", titleToFind));
    }

    private void updateBookDetails(Book book, UpdateBookDetailsValueObject updateBookDetailsValueObject) {
        book.setTitle(updateBookDetailsValueObject.title());
        book.setAuthor(updateBookDetailsValueObject.author());
        book.setPrice(updateBookDetailsValueObject.price());
    }

    public void removeBookByTitle(String bookTitle) {
        List<Book> bookList = bookRepository.getBookList();
        Book bookToDelete = getBookByTitle(bookTitle);
        bookList.remove(bookToDelete);
    }

    protected List<Book> filterBooksByTitle(List<Book> bookList, String bookTitle) {
        List<Book> filteredBookList = new ArrayList<>();
        for (Book book : bookList) {
            if (bookHasTitle(book, bookTitle)) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

    private boolean bookHasTitle(Book book, String bookTitle) {
        BookDetails bookDetails = book.getBookDetails();
        return bookDetails.getTitle().equalsIgnoreCase(bookTitle);
    }
}