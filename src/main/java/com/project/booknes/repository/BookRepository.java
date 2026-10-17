package com.project.booknes.repository;


import com.project.booknes.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenreIgnoreCase(String genre);
    boolean existsByTitleAndAuthor(String title, String author);
}
