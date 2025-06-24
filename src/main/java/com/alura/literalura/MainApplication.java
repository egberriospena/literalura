package com.alura.literalura;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que ejecuta la aplicación LiterAlura en modo consola.
 * Implementa un menú interactivo con opciones de búsqueda y consulta de libros y autores.
 */
@SpringBootApplication
@EnableJpaRepositories("com.alura.literalura.repository")
@EntityScan("com.alura.literalura.model")
public class MainApplication implements CommandLineRunner {

    private final BookService bookService;

    public MainApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    /**
     * Método principal que corre al iniciar el programa. Llama al menú.
     *
     * @param args Argumentos de línea de comando (no usados).
     */
    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            printMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1 -> searchAndSaveBook(scanner);
                    case 2 -> listAllBooks();
                    case 3 -> listAllAuthors();
                    case 4 -> listAuthorsAliveInYear(scanner);
                    case 5 -> listBooksByLanguage(scanner);
                    case 0 -> System.out.println("👋 ¡Hasta luego!");
                    default -> System.out.println("⚠️ Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes ingresar un número válido.");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("""
                
                📚 MENÚ PRINCIPAL - LiterAlura
                --------------------------------
                1 - Buscar y registrar libro por título
                2 - Listar todos los libros registrados
                3 - Listar todos los autores
                4 - Listar autores vivos en un año específico
                5 - Listar libros por idioma
                0 - Salir
                Elige una opción:
                """);
    }

    private void searchAndSaveBook(Scanner scanner) {
        System.out.print("🔎 Ingresa el título del libro: ");
        String title = scanner.nextLine();
        bookService.searchAndSaveBook(title);
    }

    private void listAllBooks() {
        List<Book> books = bookService.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("📭 No hay libros registrados.");
        } else {
            System.out.println("📚 Libros registrados:");
            books.forEach(System.out::println);
        }
    }

    private void listAllAuthors() {
        List<Author> authors = bookService.listAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("📭 No hay autores registrados.");
        } else {
            System.out.println("👤 Autores registrados:");
            authors.forEach(System.out::println);
        }
    }

    private void listAuthorsAliveInYear(Scanner scanner) {
        System.out.print("📅 Ingresa el año a consultar: ");
        try {
            int year = Integer.parseInt(scanner.nextLine());
            List<Author> authors = bookService.listAuthorsAliveInYear(year);
            if (authors.isEmpty()) {
                System.out.println("📭 No hay autores vivos en ese año.");
            } else {
                System.out.println("📊 Total de autores vivos en " + year + ": " + authors.size());
                authors.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Debes ingresar un año válido.");
        }
    }

    private void listBooksByLanguage(Scanner scanner) {
        System.out.print("🌐 Ingresa el código de idioma (ej: EN, ES, PT): ");
        String lang = scanner.nextLine();
        List<Book> books = bookService.listBooksByLanguage(lang);
        if (books.isEmpty()) {
            System.out.println("📭 No hay libros registrados en ese idioma.");
        } else {
            System.out.println("📊 Total de libros en " + lang.toUpperCase() + ": " + books.size());
            books.forEach(System.out::println);
        }
    }
}