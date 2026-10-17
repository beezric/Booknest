package com.project.booknes.controller;


import com.project.booknes.dto.BookUpdateDTO;
import com.project.booknes.dto.BookRequestDTO;
import com.project.booknes.dto.BookResponseDTO;
import com.project.booknes.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO responseDTO = bookService.getBookById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByGenre(@PathVariable String genre) {
        return new ResponseEntity<>(bookService.getBooksByGenre(genre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        BookResponseDTO savedBook = bookService.createBook(bookRequestDTO);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody BookUpdateDTO updatedBookDTO) {

        return new ResponseEntity<>(bookService.updateBook(id, updatedBookDTO),
                HttpStatus.OK);
    }
}
