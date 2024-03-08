package org.example.service;

import org.example.exception.BookException;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.List;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();

    public void logBookInfo(List<Book> bookList) {
        for (Book book : bookList) {
            book.logBookInfo();
        }
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
}