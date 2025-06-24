package com.alura.literalura.model;

import com.alura.literalura.dto.AuthorDto;
import jakarta.persistence.*;

/**
 * Representa un autor en el sistema LiterAlura.
 */
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;

    public Author() {}

    /**
     * Crea un autor a partir de un DTO recibido de la API externa.
     *
     * @param dto Objeto con los datos del autor.
     */
    public Author(AuthorDto dto) {
        this.name = dto.name();
        this.birthYear = dto.birthYear();
        this.deathYear = dto.deathYear();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    @Override
    public String toString() {
        return name + " (" + birthYear + " - " + (deathYear != null ? deathYear : "?") + ")";
    }
}