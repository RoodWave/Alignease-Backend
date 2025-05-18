package com.alignease.v1.dto;

import com.alignease.v1.entity.Product;
import com.alignease.v1.entity.ProductBooking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBookingDTO {
    private ProductBooking productBooking;
    private Product product;
}