package com.project.booknes.mapper;

import com.project.booknes.dto.BookUpdateDTO;
import com.project.booknes.dto.BookRequestDTO;
import com.project.booknes.dto.BookResponseDTO;
import com.project.booknes.model.Book;
import org.springframework.stereotype.Component;


@Component
public class BookMapper {


    public Book toBookEntity(BookRequestDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        book.setGenre(bookDTO.getGenre());

        return book;
    }

    public BookResponseDTO toBookResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getGenre(),
                book.getDescription()
        );
    }

    public void updateBookFromDTO(BookUpdateDTO dto, Book book) {

        if(dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }

        if(dto.getAuthor() != null) {
            book.setAuthor(dto.getAuthor());
        }

        if(dto.getIsbn() != null) {
            book.setIsbn(dto.getIsbn());
        }

        if(dto.getGenre() != null) {
            book.setGenre(dto.getGenre());
        }

        if(dto.getDescription() != null) {
            book.setDescription(dto.getDescription());
        }

    }
}
