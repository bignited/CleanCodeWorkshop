package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BookStore {
    private List<Book> books;
    private List<Review> reviews;
    private int booksSold;
    private double totalRevenue;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public BookStore() {
        this.books = new ArrayList<>();
        this.booksSold = 0;
        this.totalRevenue = 0.0;
    }

    public int getBooksSold() {
        return booksSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalBooks() {
        return books.size();
    }

    public void addBook(Book book) {
        if (validateBookTitle(book.getTitle())) {
            books.add(book);
            System.out.println("Book added: " + book.getTitle());
        }
    }

    public Book getBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }


    public void removeBook(String title) {
        Book book = getBookByTitle(title);
        if (book != null) {
            books.remove(book);
        }
    }

    public double calculateTotalPriceOfAllBooks() {
        double total = 0.0;
        for (Book book : books) {
            total += book.getPrice();
        }
        return total;
    }

    public void sortBooksByPrice() {
        books.sort(Comparator.comparingDouble(Book::getPrice));
    }

    public double calculateAveragePriceOfAllBooks() {

        double total = 0.0;

        if (books.isEmpty()) {
            return total;
        }

        for (Book book : books) {
            total += book.getPrice();
        }

        return total / books.size();
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayBooksSortedByPriceOrRating(boolean sortByPrice) {
        if (sortByPrice) {
            this.sortBooksByPrice();
        } else {
            this.sortBooksByRating();
        }
        this.displayBooks();
    }

    public void sellBook(String title) {
        Book book = getBookByTitle(title);
        if (book != null) {
            totalRevenue += book.getPrice();
            books.remove(book);
            booksSold++;
        }
    }

    public boolean onlyAddBookIfNotNull(Book book) {
        boolean shouldAddBook = (book != null);

        if (shouldAddBook) {
            books.add(book);
        }

        return shouldAddBook;
    }

    public List<Book> getAmountOfTopRatedBooks(int amountOfBooksInTopRatedList) {
        return books.stream()
                .sorted(Comparator.comparingDouble(Book::getRating).reversed())
                .limit(amountOfBooksInTopRatedList)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByRatingRange(double minRating, double maxRating) {
        return books.stream()
                .filter(book -> book.getRating() >= minRating && book.getRating() <= maxRating)
                .sorted(Comparator.comparingDouble(Book::getRating).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> getBooksSortedByPrice() {
        return books.stream()
                .sorted(Comparator.comparingDouble(Book::getPrice))
                .collect(Collectors.toList());
    }

    public void updateBookPrice(String title, double newPrice) {
        if (title != null) {
            if (newPrice > 0) {
                for (Book book : this.books) {
                    if (book.getTitle().equals(title)) {
                        book.setPrice(newPrice);
                        return;
                    }
                }
            }
        }
        System.out.println("Failed to update book price");
    }


    public void processBooks() {
        // code to read books from a file
        Gson gson = new Gson();
        try {
            Type bookListType = new TypeToken<List<Book>>() {
            }.getType();
            List<Book> books = gson.fromJson(new FileReader("src/main/java/org/example/books.json"), bookListType);
            for (Book book : books) {
                this.addBook(book);
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        // code to display books
        this.displayBooks();

        // code to sort books by price
        this.sortBooksByPrice();

        // code to display books again
        this.displayBooks();
    }

    public Book getBookByTitleAndAuthor(String title, String author) {

        if (title != null && author != null) {
            Book book = getBookByTitle(title);
            if (book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }

    public double calculateAverageRatingOfAllBooks() {
        double total = 0.0;

        if (books.isEmpty()) {
            return total;
        }

        for (Book book : books) {
            total += book.getRating();
        }

        return total / books.size();
    }

    public void sortBooksByRating() {
        books.sort(Comparator.comparingDouble(Book::getRating).reversed());
    }

    public void updateBookPrice(Book book, double newPrice) {
        book.setPrice(newPrice);
    }

    public void sellBooks(String title) {
        Book book = getBookByTitle(title);
        if (book != null) {
            this.books.remove(book);
            this.booksSold++;
            this.totalRevenue += book.getPrice();
        } else {
            System.out.println("Book not found");
        }
    }

    public void removeBookByTitleAndAuthor(String title, String author) {
        if (title != null) {
            if (author != null) {
                for (Book book : this.books) {
                    if (book.getTitle().equals(title)) {
                        if (book.getAuthor().equals(author)) {
                            this.books.remove(book);
                            System.out.println("Book removed: " + book.getTitle());
                            return;
                        }
                    }
                }
            }
        }
    }

    public void sellBook(String title, String author) {
        try {
            if (title != null) {
                if (author != null) {
                    for (Book book : this.books) {
                        if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                            this.books.remove(book);
                            System.out.println("Book sold: " + book.getTitle());
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    public boolean validateBookTitle(String title) {
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[ .,!?'\"-])[a-zA-Z0-9 .,!?'\"-]{1,50}$");
        Matcher matcher = pattern.matcher(title);

        return matcher.matches();
    }

    public void printReceipt(Book book) {
        System.out.println("Receipt: ");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice());
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getBooksByGenre(String genre) {
        List<Book> result = new ArrayList<>();
        for (Book book : this.books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                result.add(book);
            }
        }
        return result;
    }

    public double calculateTotalSalesForYear(int year) {
        double totalSales = 0;
        for (Book book : this.books) {
            if (book.getYearSold() == year) {
                totalSales += book.getPrice() * book.getSales();
            }
        }
        return totalSales;
    }

    public void applyDiscountToOldBooks(double discountRate) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        for (Book book : this.books) {
            if (book.getPublishDate().isBefore(oneYearAgo)) {
                double newPrice = book.getPrice() * (1 - discountRate);
                book.setPrice(newPrice);
            }
        }
    }

    public void restockBook(Book book, int additionalQuantity) {
        book.setQuantity(book.getQuantity() + additionalQuantity);
    }

    public double calculateAverageRatingOfOneBook(Book book) {
        List<Review> reviews = (List<Review>) book.getReviews();
        if (reviews.isEmpty()) {
            return 0;
        }

        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        return totalRating / reviews.size();
    }

    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Book book : this.books) {
            totalRevenue += book.getPrice() * booksSold;
        }

        return totalRevenue;
    }

    public Book findBestSellingBook() {
        if (books.isEmpty()) {
            return null;
        }

        Book bestSellingBook = books.get(0);
        for (Book book : books) {
            if (book.getNumberOfCopiesSold() > bestSellingBook.getNumberOfCopiesSold()) {
                bestSellingBook = book;
            }
        }

        return bestSellingBook;
    }
}