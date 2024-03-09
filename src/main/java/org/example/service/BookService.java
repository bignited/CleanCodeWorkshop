package org.example.service;

import org.example.exception.BookException;
import org.example.model.Book;
import org.example.model.BookDetails;
import org.example.repository.BookRepository;
import org.example.valueobject.UpdateBookDetailsValueObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();

    public Book getBookById(int bookId) {
        List<Book> bookList = bookRepository.getBookList();
        for (Book book : bookList) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        throw new BookException(String.format("Book with ID: %d cannot be found.", bookId));
    }

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

    public List<Book> getTopRatedBooksFromBookList(List<Book> bookList, int amountOfBooks) {
        List<Book> orderedBooks = orderBooksByRatingDesc(bookList);
        return orderedBooks.subList(0, amountOfBooks);
    }

    private List<Book> orderBooksByRatingDesc(List<Book> bookList) {
        bookList.sort(Comparator.comparingDouble(Book::getRating).reversed());
        return bookList;
    }

    public void updateBookDetailsByBookId(int bookId, UpdateBookDetailsValueObject updateBookDetailsValueObject) {
        Book bookToUpdate = getBookById(bookId);
        bookToUpdate.getBookDetails().setTitle(updateBookDetailsValueObject.title());
        bookToUpdate.getBookDetails().setAuthor(updateBookDetailsValueObject.author());
        bookToUpdate.getBookDetails().setPrice(updateBookDetailsValueObject.price());
    }

    public void removeBookByTitleAndAuthor(String titleBookToRemove, String authorBookToRemove) {
        List<Book> bookList = bookRepository.getBookList();
        List<Book> filteredBookList = filterBooksByTitleAndAuthor(titleBookToRemove, authorBookToRemove);
        bookList.removeAll(filteredBookList);
    }

    private List<Book> filterBooksByTitleAndAuthor(String titleBookToRemove, String authorBookToRemove) {
        List<Book> bookList = bookRepository.getBookList();
        List<Book> filteredBookList = new ArrayList<>();
        for (Book book : bookList) {
            if (bookHasTitle(book, titleBookToRemove) && bookHasAuthor(book, authorBookToRemove)) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

    private boolean bookHasAuthor(Book book, String bookAuthor) {
        BookDetails bookDetails = book.getBookDetails();
        return bookDetails.getAuthor().equalsIgnoreCase(bookAuthor);
    }

    public List<Book> getAllBooksOrderedByPrice() {
        List<Book> bookList = bookRepository.getBookList();
        return orderBooksByPriceDesc(bookList);
    }

    private List<Book> orderBooksByPriceDesc(List<Book> bookList) {
        bookList.sort(Comparator.comparingDouble(book -> {
            BookDetails bookDetails = ((Book) book).getBookDetails();
            return bookDetails.getPrice();
        }).reversed());
        return bookList;
    }

    public double getTotalPriceFromBooks(List<Book> bookList) {
        double totalPrice = 0;
        for (Book book : bookList) {
            BookDetails bookDetails = book.getBookDetails();
            totalPrice += bookDetails.getPrice();
        }
        return totalPrice;
    }
}