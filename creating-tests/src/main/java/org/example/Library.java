package org.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public List<Book> findBooksByYear(int year) {
        return books.stream()
                .filter(book -> book.getPublicationYear() == year)
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookWithMostPages() {
        return books.stream()
                .max((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()));
    }

    public List<Book> findBooksWithMoreThanNPages(int n) {
        return books.stream()
                .filter(book -> book.getPages() > n)
                .collect(Collectors.toList());
    }

    public List<String> getAllBookTitlesSorted() {
        return books.stream()
                .map(Book::getTitle)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByCategory(String category) {
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public boolean loanBook(String title) {
        Optional<Book> bookOptional = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .filter(book -> !book.isOnLoan())
                .findFirst();

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setOnLoan(true);
            return true;
        }

        return false;
    }

    public boolean returnBook(String title) {
        Optional<Book> bookOptional = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .filter(Book::isOnLoan)
                .findFirst();

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setOnLoan(false);
            return true;
        }

        return false;
    }

    public double calculateLateFees(String title) {
        Optional<Book> bookOptional = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .filter(Book::isOnLoan)
                .findFirst();

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            long daysOnLoan = ChronoUnit.DAYS.between(book.getLoanDate(), LocalDate.now());
            if (daysOnLoan > 14) {
                return (daysOnLoan - 14) * 0.50;
            }
        }

        return 0.0;
    }
}