package org.example.model;

public class Review {
    private static int idCounter = 0;
    private final int id;
    private String description;
    private double rating;

    public Review(String description, double rating) {
        this.id = idCounter;
        idCounter++;
        this.description = description;
        this.rating = rating;
    }
}
