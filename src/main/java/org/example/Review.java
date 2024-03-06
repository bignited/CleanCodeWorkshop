package org.example;

import java.util.Collection;

public class Review {
    private String description;
    private double rating;

    public Review(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }
}
