package com.software.integration.repository;

import com.software.integration.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleIgnoreCaseContaining(String title);
    List<Book> findByLanguages(String language);
}