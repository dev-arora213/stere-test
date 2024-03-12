package com.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.RegisterBookDto;
import com.store.model.Book;
import com.store.service.BookService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Add a book
    @PostMapping
    // @RolesAllowed("ROLE_ADMIN")
    public Book createBook(@RequestBody RegisterBookDto book) {
        System.out.println("book" + book);
        return bookService.createBook(book);
    }

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get book by ID
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // Update book by ID
    @PutMapping("/{id}")
    // @RolesAllowed("ROLE_ADMIN")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    // Delete all books
    @DeleteMapping
    // @RolesAllowed("ROLE_ADMIN")
    public String deleteAllBooks() {
        bookService.deleteAllBooks();
        return "All books have been deleted successfully.";
    }

    // Delete book by ID
    @DeleteMapping("/{id}")
    // @RolesAllowed("ROLE_ADMIN")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
