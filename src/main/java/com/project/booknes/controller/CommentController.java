package com.project.booknes.controller;


import com.project.booknes.dto.CommentRequestDTO;
import com.project.booknes.dto.CommentResponseDTO;
import com.project.booknes.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByListing(@PathVariable Long listingId) {
        return new ResponseEntity<>(commentService.getCommentsByListing(listingId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody
                                                                @Valid CommentRequestDTO requestDTO) {
        return new ResponseEntity<>(commentService.createComment(requestDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, Principal connectedUser) {
        commentService.deleteComment(connectedUser, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
