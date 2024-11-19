package com.software.integration.controller;

import com.software.integration.service.BookService;
import com.software.integration.service.AuthorService;
import com.software.integration.service.GutendexService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GutendexService gutendexService;

    @Autowired
    public ConsoleController(BookService bookService, AuthorService authorService, GutendexService gutendexService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.gutendexService = gutendexService;
    }

    @PostConstruct
    public void startConsole() {

        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nSelecciona una opción:");
                System.out.println("1. Buscar libro por título");
                System.out.println("2. Listar libros registrados");
                System.out.println("3. Listar autores registrados");
                System.out.println("4. Listar autores vivos en un determinado año");
                System.out.println("5. Listar libros por idioma");
                System.out.println("6. Obtener libros de la API de Gutendex y guardarlos");
                System.out.println("7. Salir");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Capturar newline

                switch (choice) {
                    case 1:
                        System.out.print("\nIngrese el título del libro: ");
                        String title = scanner.nextLine();
                        var books = bookService.getBooksByTitle(title);
                        if (books.isEmpty()) {
                            System.out.println("\n⛔ Libro no encontrado.");
                        } else {
                            // Imprimir resultados con el formato solicitado
                            books.forEach(book -> {
                                System.out.println("\n------ LIBRO ------");
                                System.out.println("Titulo: " + book.getTitle());
                                System.out.println("Autor: " + book.getAuthors());
                                System.out.println("Idioma: " + book.getLanguages());
                                System.out.println("Numero de descargas: " + book.getDownloadCount());
                                System.out.println("-------------------");
                            });
                        }
                        break;
                    case 2:
                        System.out.println("\nListando libros registrados:");
                        bookService.encontrarTodos().forEach(book -> {
                            System.out.println("\n------ LIBRO ------");
                            System.out.println("Titulo: " + book.getTitle());
                            System.out.println("Idioma: " + book.getLanguages());
                            System.out.println("-------------------");
                        });
                        break;
                    case 3:
                        System.out.println("\nEn construcción...");
                        break;
                    case 4:
                        System.out.print("\nIngrese el año: ");
                        int year = scanner.nextInt();
                        authorService.getAuthorsByYear(year).forEach(System.out::println);
                        break;
                    case 5:
                        System.out.print("\nIngrese el idioma (código ISO): ");
                        String language = scanner.nextLine();
                        System.out.println("\nListando libros en idioma " + language + ":");
                        bookService.getBooksByLanguage(language).forEach(book -> {
                            System.out.println("\n------ LIBRO ------");
                            System.out.println("Titulo: " + book.getTitle());
                            System.out.println("Autor: " + book.getAuthors());
                            System.out.println("Idioma: " + book.getLanguages());
                            System.out.println("Numero de descargas: " + book.getDownloadCount());
                            System.out.println("-------------------");
                        });
                        break;
                    case 6:
                        System.out.println("\nObteniendo libros de la API de Gutendex y guardándolos...");
                        gutendexService.fetchBooksFromApiAndSave();
                        break;
                    case 7:
                        System.out.println("\n¡Adiós!");
                        return;
                    default:
                        System.out.println("\n❌ Opción no válida.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("*******************El error esta aqui*************************" + e);
        }

    }
}
