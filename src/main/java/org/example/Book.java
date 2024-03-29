package org.example;

import java.util.Collection;
import java.util.Date;

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
    private Collection<Review> reviews;
    private int numberOfCopiesSold;


    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

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
    
    public double getRating() {
        return rating;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public int fetchYear() {
        return year;
    }

    public int retrieveYear() {
        return year;
    }

    public String fetchTitle() {
        return title;
    } 

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(int year) {
        this.year = year;
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

    public int getYearSold() {
        return yearSold;
    }

    public void setYearSold(int yearSold) {
        this.yearSold = yearSold;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public int getNumberOfCopiesSold() {
        return numberOfCopiesSold;
    }

    public void setNumberOfCopiesSold(int numberOfCopiesSold) {
        this.numberOfCopiesSold = numberOfCopiesSold;
    }
}