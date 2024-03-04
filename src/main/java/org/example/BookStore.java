package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookStore {
    private List<Book> books;
    private int booksSold;
    private double totalRevenue;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public BookStore() {
        this.books = new ArrayList<>();
        this.booksSold = 0;
        this.totalRevenue = 0.0;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public void updateBookDetails(String title, String newTitle, String newAuthor, double newPrice) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setPrice(newPrice);
        }
    }

    public void removeBook(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            books.remove(book);
        }
    }

    public int getTotalBooks() {
        return books.size();
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Book book : books) {
            total += book.getPrice();
        }
        return total;
    }

    public void sortBooksByPrice() {
        books.sort(Comparator.comparingDouble(Book::getPrice));
    }

    public double calculateAveragePrice() {
        if (books.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Book book : books) {
            total += book.getPrice();
        }
        return total / books.size();
    }

    public void displayBooks() {
        for (Book book : books) {
            LOGGER.info(book.toString());
//            System.out.println(book);
        }
    }

    public void sellBook(String title){
        Book book = searchBookByTitle(title);
        if (book != null){
            books.remove(book);
            booksSold++;
            totalRevenue += book.getPrice();
        }
    }

    public List<Book> getTopRatedBooks(int topN) {
        return books.stream()
                .sorted(Comparator.comparingDouble(Book::getRating).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
    public int getBooksSold(){
        return booksSold;
    }

    public double getTotalRevenue(){
        return totalRevenue;
    }

    public List<Book> getBooksByRatingRange(double minRating, double maxRating) {
        return books.stream()
                .filter(book -> book.getRating() >= minRating && book.getRating() <= maxRating)
                .sorted(Comparator.comparingDouble(Book::getRating).reversed())
                .collect(Collectors.toList());
    }
}