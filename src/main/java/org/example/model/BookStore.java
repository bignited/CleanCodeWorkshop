package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BookStore {
    private int id;
    private final List<Book> bookList;
    private int totalBooksSold;
    private double totalRevenue;

    public int getId() {
        return id;
    }

    public BookStore() {
        this.bookList = new ArrayList<>();
    }

    public int getTotalBooksSold() {
        return totalBooksSold;
    }

    public void setTotalBooksSold(int totalBooksSold) {
        this.totalBooksSold = totalBooksSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}