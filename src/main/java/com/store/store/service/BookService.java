package com.store.store.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.store.store.dto.RegisterBookDto;
import com.store.store.model.Book;
import com.store.store.model.BooksCategory;
import com.store.store.model.CategoryName;
import com.store.store.repository.BookRepository;
import com.store.store.repository.BooksCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BooksCategoryRepository booksCategoryRepository;

    public BookService(BookRepository bookRepository, BooksCategoryRepository booksCategoryRepository) {
        this.bookRepository = bookRepository;
        this.booksCategoryRepository = booksCategoryRepository;
    }

    @Transactional
    public Book createBook(RegisterBookDto registerBookDto) {
        Book bookData = new Book(registerBookDto.getName(), registerBookDto.getDescription(),
                registerBookDto.getImg(),
                registerBookDto.getPrice());
        Set<String> categories = registerBookDto.getCategory();
        Set<BooksCategory> categoryNames = new HashSet<>();
        for (String category : categories) {
            BooksCategory categoryName = booksCategoryRepository
                    .findByName(CategoryName.valueOf(category.toUpperCase()))
                    .orElseThrow(() -> new DataAccessResourceFailureException("Category is not found."));
            categoryNames.add(categoryName);
        }
        bookData.setCategories(categoryNames);
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
            existingBook.setCategories(BookDetails.getCategories());
            existingBook.setPrice(BookDetails.getPrice());
            return bookRepository.save(existingBook);
        }
        return null;
    }
}
