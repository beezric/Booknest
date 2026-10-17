package com.project.booknes.service;

import com.project.booknes.dto.BookUpdateDTO;
import com.project.booknes.dto.BookRequestDTO;
import com.project.booknes.dto.BookResponseDTO;
import com.project.booknes.exception.ResourceDuplicationException;
import com.project.booknes.exception.ResourceNotFoundException;
import com.project.booknes.mapper.BookMapper;
import com.project.booknes.model.Book;
import com.project.booknes.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookResponseDTO> getAllBooks() {
        return this.bookRepository.findAll().stream()
                .map(bookMapper::toBookResponseDTO)
                .toList();
    }

    public List<BookResponseDTO> getBooksByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre).stream()
                .map(bookMapper::toBookResponseDTO)
                .toList();
    }


    public BookResponseDTO createBook(BookRequestDTO bookDTO) {

        if(bookRepository.existsByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor())) {
            throw new ResourceDuplicationException("Book Title: " + bookDTO.getTitle() + " " +
                    "and Book Author: " + bookDTO.getAuthor() + " already exists.");
        }


        Book newBook = bookMapper.toBookEntity(bookDTO);

        Book savedBook = bookRepository.save(newBook);

        return bookMapper.toBookResponseDTO(savedBook);
    }
    

    public BookResponseDTO getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID: " +
                       id + " does not exists."));

        return bookMapper.toBookResponseDTO(book);
    }

    public void deleteBook(Long id) {

        if(!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book with ID: " + id + " does not exist.");
        }

        bookRepository.deleteById(id);
    }

    public BookResponseDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {


            Book existingBook = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book with ID: " + id +
                            " does not exist"));


            bookMapper.updateBookFromDTO(bookUpdateDTO, existingBook);

            Book savedBook = bookRepository.save(existingBook);
            return bookMapper.toBookResponseDTO(savedBook);
    }
}
