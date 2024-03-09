package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BookStore {
    private static int idCounter = 0;
    private int id;
    private final String name;
    private int totalBooksSold;
    private double totalRevenue;
    private final List<Book> bookList;

    public BookStore(String name) {
        this.id = idCounter;
        idCounter++;
        this.name = name;
        this.bookList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public int getTotalBooksSold() {
        return totalBooksSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalBooksSold(int totalBooksSold) {
        this.totalBooksSold = totalBooksSold;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}