package com.alura.literalura.model;

import com.alura.literalura.dto.BookDto;
import jakarta.persistence.*;

/**
 * Representa un libro registrado en la base de datos.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;
    private Integer downloadCount;

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

    public Book() {}

    /**
     * Crea un libro a partir de los datos proporcionados por la API externa.
     *
     * @param dto Objeto con los datos del libro.
     */
    public Book(BookDto dto) {
        this.title = dto.title();
        this.language = (dto.language() == null || dto.language().isBlank())
                ? "unknown"
                : dto.language().toUpperCase();
        this.downloadCount = dto.downloadCount();
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "📘 Libro: " + title +
                "\n🌐 Idioma: " + language +
                "\n⬇️ Descargas: " + downloadCount +
                "\n✍️ Autor: " + (author != null ? author.getName() : "Desconocido") + "\n";
    }
}