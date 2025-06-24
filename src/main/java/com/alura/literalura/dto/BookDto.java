package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO que representa un libro devuelto por la API de Gutendex.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDto(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorDto> authors,
        @JsonAlias("language") String language,
        @JsonAlias("download_count") int downloadCount
) {
}