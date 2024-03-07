package org.example.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private double price;
    private double averageRating;
    private String publisher;
    private String bookGenre;
    private Date publishDate;
    private List<BookReview> bookReviews;
    private int numberOfCopiesSold;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }
    
    public double getAverageRating() {
        return averageRating;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookGenre getBookGenre() {
        return this.bookGenre;
    }

    public String fetchTitle() {
        return title;
    } 

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setBookGenre(BookGenre bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Collection<BookReview> getReviews() {
        return bookReviews;
    }

    public void setReviews(List<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public int getNumberOfCopiesSold() {
        return numberOfCopiesSold;
    }

    public void setNumberOfCopiesSold(int numberOfCopiesSold) {
        this.numberOfCopiesSold = numberOfCopiesSold;
    }

    public void printInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Price: " + price);
    }
}