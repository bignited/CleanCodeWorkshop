package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private static int idCounter = 0;
    private final int id;
    private double rating;
    private int numberOfCopiesSold;
    private final BookDetails bookDetails;
    private final List<Review> reviewList;

    public Book(String title, String author, double price) {
        this.id = idCounter;
        idCounter++;
        this.bookDetails = new BookDetails(title, author, price);
        this.reviewList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public int getNumberOfCopiesSold() {
        return numberOfCopiesSold;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setNumberOfCopiesSold(int numberOfCopiesSold) {
        this.numberOfCopiesSold = numberOfCopiesSold;
    }
}