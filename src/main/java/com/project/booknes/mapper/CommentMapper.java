package com.project.booknes.mapper;

import com.project.booknes.dto.CommentRequestDTO;
import com.project.booknes.dto.CommentResponseDTO;
import com.project.booknes.dto.UserCommentSummaryDTO;
import com.project.booknes.model.Comment;
import com.project.booknes.model.Listing;
import com.project.booknes.model.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDTO toResponseDTO(Comment comment) {

        UserCommentSummaryDTO author = new UserCommentSummaryDTO(
                comment.getUser().getId(),
                comment.getUser().getUsername()
        );

        return new CommentResponseDTO(
                comment.getId(),
                comment.getMessage(),
                comment.getCreatedAt(),
                comment.getListing().getId(),
                author
        );
    }

    public Comment toEntity(CommentRequestDTO dto, Listing listing, User user) {

        Comment comment = new Comment();
        comment.setMessage(dto.getMessage());
        comment.setListing(listing);
        comment.setUser(user);

        return comment;
    }


}
