package org.example.model;

import org.example.service.LoggingService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private double price;
    private double rating;
    private int year;
    private String publisher;
    private String genre;
    private int sales;
    private int yearSold;
    private Date publishDate;
    private int quantity;
    private List<Review> reviews;
    private int numberOfCopiesSold;


    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String fetchTitle() {
        return title;
    }

    public int getYearSold() {
        return yearSold;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public int getNumberOfCopiesSold() {
        return numberOfCopiesSold;
    }

    public void logBookInfo() {
        String bookInfo = String.format("Title: %s, Author: %s, Price: %.2f, Rating: %.2f",
                title, author, price, rating);
        LoggingService.logInfo(bookInfo, this.getClass());
    }

    public void increaseCopiesSold(int amountOfSoldCopies) {
        this.numberOfCopiesSold += amountOfSoldCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }
}