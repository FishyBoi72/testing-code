package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        library = new Library();
        book1 = new Book("Book One", "Author A", 2020, 300, "Fiction");
        book2 = new Book("Book Two", "Author B", 2018, 150, "Science");
        book3 = new Book("Book Three", "Author A", 2022, 400, "Fiction");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
    }

    @Test
    void testFindBooksByYear() {
        List<Book> result = library.findBooksByYear(2020);
        assertEquals(1, result.size());
        assertEquals("Book One", result.get(0).getTitle());
    }

    @Test
    void testFindBooksByAuthor() {
        List<Book> result = library.findBooksByAuthor("Author A");
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book3));
    }

    @Test
    void testLoanBook() {
        assertTrue(library.loanBook("Book One"));
        assertTrue(book1.isOnLoan());
        assertFalse(library.loanBook("Book One")); // Can't loan again
    }

    @Test
    void testReturnBook() {
        library.loanBook("Book One");
        assertTrue(library.returnBook("Book One"));
        assertFalse(book1.isOnLoan());
        assertFalse(library.returnBook("Book One")); // Can't return again
    }

    @Test
    void testCalculateLateFees() {
        library.loanBook("Book One");
        book1.setLoanDate(LocalDate.now().minusDays(20)); // Simulate a loan 20 days ago

        double lateFees = library.calculateLateFees("Book One");
        assertEquals(3.0, lateFees);
    }
}
