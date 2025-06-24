package com.alura.literalura.service;

import com.alura.literalura.client.GutendexClient;
import com.alura.literalura.dto.BookDto;
import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de manejar la lógica de negocio relacionada a libros.
 */
@Service
public class BookService {

    private final GutendexClient gutendexClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(GutendexClient gutendexClient,
                       BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.gutendexClient = gutendexClient;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Busca un libro en la API por su título y lo guarda si no está duplicado.
     *
     * @param title Título del libro a buscar.
     * @return El libro guardado o null si no se encontró o ya estaba registrado.
     */
    public Book searchAndSaveBook(String title) {
        Optional<BookDto> bookDtoOpt = gutendexClient.searchBookByTitle(title);

        if (bookDtoOpt.isEmpty()) {
            System.out.println("❌ Libro no encontrado en la API.");
            return null;
        }

        BookDto dto = bookDtoOpt.get();
        String authorName = dto.authors().isEmpty() ? "Desconocido" : dto.authors().get(0).name();

        Optional<Book> existing = bookRepository.findByTitleAndAuthorName(dto.title(), authorName);
        if (existing.isPresent()) {
            System.out.println("⚠️ El libro ya está registrado en la base de datos.");
            return null;
        }

        Book book = new Book(dto);
        Author existingAuthor = authorRepository.findByName(authorName);
        if (existingAuthor != null) {
            book.setAuthor(existingAuthor);
        }

        Book saved = bookRepository.save(book);
        System.out.println("✅ Libro registrado: \n" + saved);
        return saved;
    }

    /**
     * Retorna todos los libros registrados.
     */
    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retorna todos los autores registrados.
     */
    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Filtra autores vivos en un año específico usando Streams.
     */
    public List<Author> listAuthorsAliveInYear(int year) {
        return authorRepository.findAll().stream()
                .filter(a -> a.getBirthYear() != null && a.getDeathYear() != null)
                .filter(a -> a.getBirthYear() <= year && a.getDeathYear() >= year)
                .toList();
    }

    /**
     * Filtra libros por idioma, ignorando registros con language null.
     *
     * @param language Código de idioma (ej: "EN", "ES").
     * @return Lista de libros filtrados por idioma.
     */
    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getLanguage() != null && b.getLanguage().equalsIgnoreCase(language))
                .toList();
    }

    /**
     * Devuelve la cantidad de libros en un idioma.
     *
     * @param language Código de idioma a contar.
     * @return cantidad de libros.
     */
    public long countBooksByLanguage(String language) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getLanguage() != null && b.getLanguage().equalsIgnoreCase(language))
                .count();
    }

    /**
     * Devuelve la cantidad de autores vivos en un año específico.
     *
     * @param year Año a consultar.
     * @return cantidad de autores vivos.
     */
    public long countAuthorsAliveInYear(int year) {
        return authorRepository.findAll().stream()
                .filter(a -> a.getBirthYear() != null && a.getDeathYear() != null)
                .filter(a -> a.getBirthYear() <= year && a.getDeathYear() >= year)
                .count();
    }
}