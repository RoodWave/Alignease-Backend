package com.alignease.v1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private Integer quantity;
    private String location;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    @JsonBackReference
    private Product product;
}
