package com.project.booknes.mapper;

import com.project.booknes.dto.*;
import com.project.booknes.model.Book;
import com.project.booknes.model.Listing;
import com.project.booknes.model.User;
import org.springframework.stereotype.Component;

@Component
public class ListingMapper {


    public ListingResponseDTO toResponseDTO(Listing listing) {

        SellerSummaryDTO sellerSummary = new SellerSummaryDTO(
                listing.getSeller().getId(),
                listing.getSeller().getUsername()
        );

        BookSummaryDTO bookSummary = new BookSummaryDTO(
                listing.getBook().getId(),
                listing.getBook().getTitle(),
                listing.getBook().getAuthor()
        );

        return new ListingResponseDTO(
                listing.getId(),
                listing.getPrice(),
                listing.getCondition(),
                listing.getStatus(),
                listing.getCreatedAt(),
                sellerSummary,
                bookSummary
        );
    }


    public Listing toEntity(ListingRequestDTO dto, User user, Book book) {

        Listing listing = new Listing();
        listing.setPrice(dto.getPrice());
        listing.setCondition(dto.getCondition());
        listing.setSeller(user);
        listing.setBook(book);
        return listing;
    }

    public void updateListingFromDTO(ListingUpdateDTO dto, Listing listing) {

        if(dto.getPrice() != null) {
            listing.setPrice(dto.getPrice());
        }

        if(dto.getStatus() != null) {
            listing.setStatus(dto.getStatus());
        }

        if(dto.getCondition() != null) {
            listing.setCondition(dto.getCondition());
        }
    }
}
