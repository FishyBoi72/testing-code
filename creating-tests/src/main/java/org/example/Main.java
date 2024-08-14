package org.example;

import java.time.LocalDate;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        User user1 = new User("Alice", "12345");
        User user2 = new User("Bob", "67890");

        library.addBook(new Book("Book1", "Author1", 2000, 300, "Fiction"));
        library.addBook(new Book("Book2", "Author2", 2005, 150, "Science"));
        library.addBook(new Book("Book3", "Author1", 2010, 450, "History"));

        library.loanBook("Book1");
        Book loanedBook = library.findBooksByYear(2000).get(0);
        user1.loanBook(loanedBook);

        loanedBook.setLoanDate(LocalDate.now().minusDays(20));

        System.out.println("All book titles sorted:");
        library.getAllBookTitlesSorted().forEach(System.out::println);

        System.out.println("Books by Author1:");
        library.findBooksByAuthor("Author1").forEach(book -> System.out.println(book.getTitle()));

        System.out.println("Late fees for Book1: " + library.calculateLateFees("Book1"));

        library.returnBook("Book1");
        user1.returnBook(loanedBook);
    }
}