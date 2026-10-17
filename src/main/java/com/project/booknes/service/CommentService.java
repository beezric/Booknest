package com.project.booknes.service;

import com.project.booknes.dto.CommentRequestDTO;
import com.project.booknes.dto.CommentResponseDTO;
import com.project.booknes.enums.Role;
import com.project.booknes.exception.ResourceNotFoundException;
import com.project.booknes.mapper.CommentMapper;
import com.project.booknes.model.Comment;
import com.project.booknes.model.Listing;
import com.project.booknes.model.User;
import com.project.booknes.repository.CommentRepository;
import com.project.booknes.repository.ListingRepository;
import com.project.booknes.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final CommentMapper commentMapper;


    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ListingRepository listingRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponseDTO createComment(CommentRequestDTO requestDTO) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        Listing listing = listingRepository.findById(requestDTO.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));


        Comment comment = commentMapper.toEntity(requestDTO, listing, user);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponseDTO(savedComment);
    }

    public void deleteComment(Principal connectedUser, Long id) {


        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        User user = userRepository.findByUsername(connectedUser.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!comment.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You can only delete your own comment.");
        }

        commentRepository.deleteById(id);
    }

    public List<CommentResponseDTO> getCommentsByListing(Long listingId) {
        return commentRepository.findByListingId(listingId).stream()
                .map(commentMapper::toResponseDTO)
                .toList();

    }
}
