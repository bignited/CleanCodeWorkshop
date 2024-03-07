package org.example.model;

public class Review {
    private String description;
    private double rating;

    public Review(String description, double rating) {
        this.description = description;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
}
