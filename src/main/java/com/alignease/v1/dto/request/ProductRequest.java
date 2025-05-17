package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String brand;
    private String model;
    private String size;
    private String price;
    private InventoryRequest inventoryRequest;
}
