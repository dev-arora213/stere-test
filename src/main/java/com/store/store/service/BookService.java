package com.store.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.store.dto.RegisterBookDto;
import com.store.store.model.Book;
import com.store.store.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(RegisterBookDto registerBookDto) {
        Book bookData = new Book(registerBookDto.getName(), registerBookDto.getDescription(), registerBookDto.getImg(),
                registerBookDto.getPrice());
        return bookRepository.save(bookData);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    public Book updateBook(Long id, Book BookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setName(BookDetails.getName());
            existingBook.setDescription(BookDetails.getDescription());
            existingBook.setPrice(BookDetails.getPrice());
            return bookRepository.save(existingBook);
        }
        return null;
    }
}
