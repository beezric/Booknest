package com.project.booknes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {

    private Long id;

    private String message;

    private LocalDateTime createdAt;

    private Long listingId;

    private UserCommentSummaryDTO author;
}
