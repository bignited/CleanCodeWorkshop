package org.example.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Main;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BookStore {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private List<Book> bookList;
    private List<Review> reviews;
    private int booksSold;
    private double totalRevenue;


    public BookStore() {
        this.bookList = new ArrayList<>();
        this.booksSold = 0;
        this.totalRevenue = 0.0;
    }

    public void addBook(Book book) {
        this.bookList.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public Book searchBookByTitle(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public void updateBookDetails(String title, String newTitle, String newAuthor, double newPrice) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setPrice(newPrice);
        }
    }

    public void removeBook(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            bookList.remove(book);
        }
    }

    public int getTotalBooks() {
        return bookList.size();
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Book book : bookList) {
            total += book.getPrice();
        }
        return total;
    }

    public void sortBooksByPrice() {
        bookList.sort(Comparator.comparingDouble(Book::getPrice));
    }

    public double calculateAveragePrice() {
        if (bookList.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Book book : bookList) {
            total += book.getPrice();
        }
        return total / bookList.size();
    }

    public void displayBooks() {
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    public void sellBook(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            bookList.remove(book);
            booksSold++;
            totalRevenue += book.getPrice();
        }
    }

    public boolean onlyAddBookIfNotNull(Book book) {
        if (book == null) {
            return false;
        }
        this.bookList.add(book);
        return true;
    }

    public List<Book> getTopRatedBooks(int topN) {
        return bookList.stream()
                .sorted(Comparator.comparingDouble(Book::getRating).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    public int getBooksSold() {
        return booksSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public List<Book> getBooksByRatingRange(double minRating, double maxRating) {
        return bookList.stream()
                .filter(book -> book.getRating() >= minRating && book.getRating() <= maxRating)
                .sorted(Comparator.comparingDouble(Book::getRating).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> getBooksSortedByPrice() {
        return bookList.stream()
                .sorted(Comparator.comparingDouble(Book::getPrice))
                .collect(Collectors.toList());
    }

    public void updateBookPrice(String title, double newPrice) {
        if (title != null) {
            if (newPrice > 0) {
                for (Book book : this.bookList) {
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

    public void addbook(Book book) {
        this.bookList.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void addBookWithVariables(Book book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        double price = book.getPrice();
        double rating = book.getRating();
        String genre = book.getGenre();
        String publisher = book.getPublisher();
        int year = book.getYear();
        addBookWithArguments(title, author, price, rating, genre, publisher, year);
    }

    public void addBookWithHighRating(Book book) {
        if (book.getRating() > 4.5) {
            this.bookList.add(book);
        }
    }

    public Book findBookByTitleAndAuthor(String title, String author) {
        if (title != null) {
            if (author != null) {
                for (Book book : this.bookList) {
                    if (book.getTitle().equals(title)) {
                        if (book.getAuthor().equals(author)) {
                            return book;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void addBookWithDeadCode(Book book) {
        this.bookList.add(book);
        if (false) {
            System.out.println("Something went wrong!");
        }
    }

    public void addBookAndCalculateAverageRating(Book book) {
        this.bookList.add(book);
        this.calculateAverageRating(); // must be called after addBook
    }

    public double calculateAverageRating() {
        if (bookList.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Book book : bookList) {
            total += book.getRating();
        }
        return total / bookList.size();
    }

    public void displayBooks(boolean sortByPrice) {
        if (sortByPrice) {
            this.sortBooksByPrice();
        } else {
            this.sortBooksByRating();
        }
        this.displayBooks();
    }

    public void sortBooksByRating() {
        bookList.sort(Comparator.comparingDouble(Book::getRating).reversed());
    }

    public void updateBookPrice(Book book, double newPrice) {
        book.setPrice(newPrice);
    }

    public void addAnotherBook(Book book) {
        // add the book to the list
        this.bookList.add(book);
        System.out.println("Book added: " + book.fetchTitle());
    }

    public void addBookWithArguments(String title, String author, double price, double rating, String genre, String publisher, int year) {
        Book book = new Book(title, author, price);
        book.setRating(rating);
        book.setGenre(genre);
        book.setPublisher(publisher);
        book.setYear(year);
        this.bookList.add(book);
    }

    public void sellBooks(String title) {
        Book book = searchBookByTitle(title);
        if (book != null) {
            this.bookList.remove(book);
            this.booksSold++;
            this.totalRevenue += book.getPrice();
        } else {
            System.out.println("Book not found");
        }
    }


    public void addBookWithTooManyStatements(Book book) {
        if (book.getRating() > 4.5) {
            this.bookList.add(book);
        } else {
            System.out.println("Book not added: " + book.getTitle());
        }
    }


    public void removeBookByTitleAndAuthor(String title, String author) {
        if (title != null) {
            if (author != null) {
                for (Book book : this.bookList) {
                    if (book.getTitle().equals(title)) {
                        if (book.getAuthor().equals(author)) {
                            this.bookList.remove(book);
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
                    for (Book book : this.bookList) {
                        if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                            this.bookList.remove(book);
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

    public void addBookIfTitleIsValid(Book book) {
        String title = book.getTitle();
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[ .,!?'\"-])[a-zA-Z0-9 .,!?'\"-]{1,50}$");
        Matcher matcher = pattern.matcher(title);

        if (matcher.matches()) {
            this.bookList.add(book);
            System.out.println("Book added: " + book.getTitle());
        } else {
            System.out.println("Book title is not valid. It should contain at least one letter, one number, and one special character.");
        }
    }

    public void printBookDetails(Book book) {
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice());
    }

    public void printReceipt(Book book) {
        System.out.println("Receipt: ");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice());
    }

    public void addBookAndPrintReceipt(Book book) {
        this.bookList.add(book);
        System.out.println("Book added: " + book.getTitle());
        printReceipt(book);
    }

    public void addBook(Book book, boolean printReceipt) {
        this.bookList.add(book);
        System.out.println("Book added: " + book.getTitle());
        if (printReceipt) {
            printReceipt(book);
        }
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : this.bookList) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> filterBooksByGenre(String genre) {
        List<Book> result = new ArrayList<>();
        for (Book book : this.bookList) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                result.add(book);
            }
        }
        return result;
    }

    public double yearlySalesReport(int year) {
        double totalSales = 0;
        for (Book book : this.bookList) {
            if (book.getYearSold() == year) {
                totalSales += book.getPrice() * book.getSales();
            }
        }
        return totalSales;
    }

    public void applyDiscountToOldBooks(double discountRate) {
        Date oneYearAgo = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(oneYearAgo);
        cal.add(Calendar.YEAR, -1); // Subtract 1 year
        oneYearAgo = cal.getTime();

        for (Book book : this.bookList) {
            if (book.getPublishDate().before(oneYearAgo)) {
                double newPrice = book.getPrice() * (1 - discountRate);
                book.setPrice(newPrice);
            }
        }
    }

    public void restockBook(Book book, int additionalQuantity) {
        book.setQuantity(book.getQuantity() + additionalQuantity);
    }


    public void addReview(Book book, Review review) {
        book.getReviews().add(review);
    }

    public double calculateAverageRating(Book book) {
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

    public void sortBooksByAverageRating() {
        Collections.sort(this.bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                double avgRating1 = calculateAverageRating(b1);
                double avgRating2 = calculateAverageRating(b2);
                return Double.compare(avgRating2, avgRating1);
            }
        });
    }

    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Book book : this.bookList) {
            totalRevenue += book.getPrice() * booksSold;
        }
        return totalRevenue;
    }

    public Book findBestSellingBook() {
        if (this.bookList.isEmpty()) {
            return null; // or you might choose to throw an exception
        }

        Book bestSellingBook = this.bookList.get(0);
        for (Book book : this.bookList) {
            if (book.getNumberOfCopiesSold() > bestSellingBook.getNumberOfCopiesSold()) {
                bestSellingBook = book;
            }
        }

        return bestSellingBook;
    }

    public void addBooks(List<Book> bookList) {
        this.bookList.addAll(bookList);
    }

    public List<Book> getBookList() {
        return bookList;
    }
}