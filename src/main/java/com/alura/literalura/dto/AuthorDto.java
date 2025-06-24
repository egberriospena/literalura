package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO que representa un autor dentro de la respuesta de la API de Gutendex.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDto(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthYear,
        @JsonAlias("death_year") Integer deathYear
) {
}