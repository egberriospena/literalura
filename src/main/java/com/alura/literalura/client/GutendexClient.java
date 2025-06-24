package com.alura.literalura.client;

import com.alura.literalura.dto.BookDto;
import com.alura.literalura.dto.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Cliente para consultar la API pública de Gutendex y obtener información de libros.
 */
@Component
public class GutendexClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Consulta la API de Gutendex buscando por título.
     *
     * @param title Título del libro a buscar.
     * @return Un Optional con el primer libro encontrado o vacío si no hay resultados.
     */
    public Optional<BookDto> searchBookByTitle(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + encodedTitle;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Error en la respuesta HTTP: " + response.statusCode());
                return Optional.empty();
            }

            GutendexResponse result = objectMapper.readValue(response.body(), GutendexResponse.class);
            return result.results().stream().findFirst();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al consumir la API: " + e.getMessage());
            return Optional.empty();
        }
    }
}