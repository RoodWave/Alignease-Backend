package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBookingRequest {
    private Long productId;
    private Long userId;
    private Integer quantity;
}
