package com.alura.literalura.controller;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que permite consultar y registrar libros mediante HTTP.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Registra un libro en la base de datos a partir de su título.
     *
     * @param title El título del libro a buscar y guardar.
     * @return El libro registrado o mensaje si ya existía.
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchAndSaveBook(@RequestParam String title) {
        Book book = bookService.searchAndSaveBook(title);
        if (book == null) {
            return ResponseEntity.badRequest().body("El libro no fue encontrado o ya está registrado.");
        }
        return ResponseEntity.ok(book);
    }

    /**
     * Lista todos los libros registrados.
     */
    @GetMapping
    public List<Book> listAllBooks() {
        return bookService.listAllBooks();
    }

    /**
     * Lista todos los autores registrados.
     */
    @GetMapping("/authors")
    public List<Author> listAllAuthors() {
        return bookService.listAllAuthors();
    }

    /**
     * Lista los autores que estaban vivos en el año indicado.
     *
     * @param year Año a consultar.
     */
    @GetMapping("/authors/alive")
    public List<Author> listAuthorsAliveIn(@RequestParam int year) {
        return bookService.listAuthorsAliveInYear(year);
    }

    /**
     * Lista libros por código de idioma (ej: EN, ES, PT).
     *
     * @param language Código de idioma.
     */
    @GetMapping("/language")
    public List<Book> listBooksByLanguage(@RequestParam String language) {
        return bookService.listBooksByLanguage(language);
    }
}