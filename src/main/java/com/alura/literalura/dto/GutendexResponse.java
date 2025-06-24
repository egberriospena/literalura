package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO raíz que encapsula la lista de libros devueltos por la API de Gutendex.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse(
        List<BookDto> results
) {
}