package com.project.booknes.dto;

import com.project.booknes.enums.BookCondition;
import com.project.booknes.enums.BookListingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListingResponseDTO {

    private Long listingId;
    private BigDecimal price;
    private BookCondition condition;
    private BookListingStatus status;
    private LocalDateTime createdAt;
    private SellerSummaryDTO seller;
    private BookSummaryDTO book;
}
