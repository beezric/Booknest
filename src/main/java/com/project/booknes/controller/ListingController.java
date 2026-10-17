package com.project.booknes.controller;

import com.project.booknes.dto.ListingRequestDTO;
import com.project.booknes.dto.ListingResponseDTO;
import com.project.booknes.dto.ListingUpdateDTO;
import com.project.booknes.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public ResponseEntity<List<ListingResponseDTO>> getAllListings() {
        return new ResponseEntity<>(listingService.getAllListings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDTO> getListingById(@PathVariable Long id) {
        return new ResponseEntity<>(listingService.getListingById(id), HttpStatus.OK);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ListingResponseDTO>> getListingsBySeller(@PathVariable Long sellerId) {
        return new ResponseEntity<>(listingService.getListingsBySeller(sellerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListingResponseDTO> createListing(@RequestBody @Valid ListingRequestDTO requestDTO) {
        return new ResponseEntity<>(listingService.createListing(requestDTO), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListing(@PathVariable Long id, Principal connectedUser) {
        listingService.deleteListing(id, connectedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ListingResponseDTO> updateListing(@PathVariable Long id,
                                                            @RequestBody @Valid
                                                            ListingUpdateDTO updateDTO,
                                                            Principal connectedUser) {

        return new ResponseEntity<>(listingService.updateListing(id, updateDTO, connectedUser), HttpStatus.OK);
    }
}
