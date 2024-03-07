package org.example.model;

public class BookReview {
    private String description;
    private double rating;

    public BookReview(String description, double rating){
        this.description = description;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
}
