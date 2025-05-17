package com.alignease.v1.dto.response;

import com.alignease.v1.entity.Product;
import com.alignease.v1.entity.ProductBooking;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse extends Response{
    private Product product;
    private List<Product> products;
    private List<ProductBooking> productBookings;
}
