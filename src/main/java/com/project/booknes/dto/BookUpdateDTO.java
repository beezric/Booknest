package com.project.booknes.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookUpdateDTO {

    @Size(min = 1)
    private String title;

    @Size(min = 1)
    private String author;
    private String isbn;
    private String genre;
    private String description;
}
