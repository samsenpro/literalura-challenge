package com.software.integration.service;

import com.software.integration.model.Book;
import com.software.integration.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleIgnoreCaseContaining(title);
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguages(language);
    }

    public List<Book> encontrarTodos() {
        return bookRepository.findAll();
    }
}