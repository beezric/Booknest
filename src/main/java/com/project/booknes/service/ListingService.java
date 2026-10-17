package com.project.booknes.service;

import com.project.booknes.dto.ListingRequestDTO;
import com.project.booknes.dto.ListingResponseDTO;
import com.project.booknes.dto.ListingUpdateDTO;
import com.project.booknes.enums.Role;
import com.project.booknes.exception.ResourceNotFoundException;
import com.project.booknes.mapper.ListingMapper;
import com.project.booknes.model.Book;
import com.project.booknes.model.Listing;
import com.project.booknes.model.User;
import com.project.booknes.repository.BookRepository;
import com.project.booknes.repository.ListingRepository;
import com.project.booknes.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public ListingService(ListingRepository listingRepository, ListingMapper listingMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<ListingResponseDTO> getAllListings() {
        return listingRepository.findAll().stream()
                .map(listingMapper::toResponseDTO)
                .toList();
    }

    public ListingResponseDTO createListing(ListingRequestDTO dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));


        Listing listing = listingMapper.toEntity(dto, seller, book);
        Listing savedListing = listingRepository.save(listing);

        return listingMapper.toResponseDTO(savedListing);
    }

    public ListingResponseDTO getListingById(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Listing By ID: " + id + " does not exist"));

        return listingMapper.toResponseDTO(listing);
    }

    public List<ListingResponseDTO> getListingsBySeller(Long id) {

        return listingRepository.findBySellerId(id).stream()
                .map(listingMapper::toResponseDTO)
                .toList();
    }

    public void deleteListing(Long id, Principal connectedUser) {

       Listing listing = listingRepository.findById(id)
                       .orElseThrow(() -> new ResourceNotFoundException("Listing with ID: " +
                              id + " not found"));

       User user = userRepository.findByUsername(connectedUser.getName())
                       .orElseThrow(() -> new ResourceNotFoundException("User not found"));

       if(!listing.getSeller().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
           throw new AccessDeniedException("You can only delete your own listing.");
       }

        listingRepository.deleteById(id);
    }

    public ListingResponseDTO updateListing(Long id, ListingUpdateDTO dto, Principal connectedUser) {

        User user = userRepository.findByUsername(connectedUser.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing with ID: " + id + " does not exist"));

        if(!existingListing.getSeller().getId().equals(user.getId()) &&
                !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You can only update your own listing.");
        }

        listingMapper.updateListingFromDTO(dto, existingListing);
        Listing updatedListing = listingRepository.save(existingListing);

        return listingMapper.toResponseDTO(updatedListing);
    }
}



