package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

/**
 * Repositorio para operaciones CRUD sobre libros.
 */

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndAuthorName(String title, String authorName);
}