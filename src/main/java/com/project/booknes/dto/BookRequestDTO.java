package com.project.booknes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "Book Title should not be empty.")
    private String title;

    @NotBlank(message = "Book Author should not be empty.")
    private String author;
    private String isbn;
    private String genre;
    private String description;
}
