package com.alignease.v1.dto.request;

import com.alignease.v1.entity.StockStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {
    private Integer quantity;
    private String location;
    private StockStatus stockStatus;
}
