package com.project.booknes.dto;


import com.project.booknes.enums.BookCondition;
import com.project.booknes.enums.BookListingStatus;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListingUpdateDTO {


    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private BookCondition condition;

    private BookListingStatus status;
}
