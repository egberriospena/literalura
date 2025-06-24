package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Author.
 * Provee métodos para consultas específicas usando derived queries.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Busca un autor por su nombre exacto.
     *
     * @param name Nombre del autor.
     * @return El autor correspondiente si existe.
     */
    Author findByName(String name);

    /**
     * Busca todos los autores que estaban vivos en un año específico.
     * Es decir, nacieron en o antes de ese año y murieron en o después del mismo.
     *
     * @param birthYear Año consultado (para comparar con nacimiento).
     * @param deathYear Año consultado (para comparar con fallecimiento).
     * @return Lista de autores vivos en ese año.
     */
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int birthYear, int deathYear);

}