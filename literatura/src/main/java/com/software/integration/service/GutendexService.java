package com.software.integration.service;

import com.software.integration.model.Author;
import com.software.integration.model.Book;
import com.software.integration.model.GutendexResponse;
import com.software.integration.repository.AuthorRepository;
import com.software.integration.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GutendexService {
    @Autowired
    private AuthorRepository authorRepository;
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;

    @Autowired
    public GutendexService(RestTemplateBuilder builder, BookRepository bookRepository) {
        this.restTemplate = builder.build();
        this.bookRepository = bookRepository;
    }

    public void fetchBooksFromApiAndSave() {
        String url = "https://gutendex.com/books";
        ResponseEntity<GutendexResponse> response = restTemplate.getForEntity(url, GutendexResponse.class);

        // Asegurarse de que el cuerpo de la respuesta no sea nulo
        if (response.getBody() != null && response.getBody().getResults() != null) {
            List<GutendexResponse.BookResponse> bookResponses = response.getBody().getResults();

            // Transformar cada respuesta en una entidad Book y guardarla
            for (GutendexResponse.BookResponse bookResponse : bookResponses) {
                Book book = mapToEntity(bookResponse);


                // Mostrar en consola antes de guardar
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autores: " + book.getAuthors());
                System.out.println("Idioma(s): " + book.getLanguages());
                System.out.println("Número de descargas: " + book.getDownloadCount());
                System.out.println("----------------------");

                bookRepository.save(book); // Guardar en la base de datos
            }
        }
    }

    // Método para transformar la respuesta de la API en una entidad Book
    private Book mapToEntity(GutendexResponse.BookResponse bookResponse) {
        Book book = new Book();
        book.setTitle(bookResponse.getTitle());
        book.setCopyright(bookResponse.isCopyright());
        book.setLanguages(bookResponse.getLanguages());
        book.setDownloadCount(bookResponse.getDownloadCount());

        // Buscar o crear autores
        List<Author> authors = bookResponse.getAuthors().stream()
                .map(authorResponse -> {
                    // Buscar autor existente por nombre
                    return authorRepository.findByName(authorResponse.getName())
                            .orElseGet(() -> {
                                Author newAuthor = new Author();
                                newAuthor.setName(authorResponse.getName());
                                newAuthor.setBirthYear(authorResponse.getBirthYear());
                                newAuthor.setDeathYear(authorResponse.getDeathYear());
                                return authorRepository.save(newAuthor);
                            });
                })
                .collect(Collectors.toList());

        book.setAuthors(authors);
        return book;
    }

}
