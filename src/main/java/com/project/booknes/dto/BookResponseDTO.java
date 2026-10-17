package com.project.booknes.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String description;
}
