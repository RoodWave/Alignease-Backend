package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ProductBookingRequest;
import com.alignease.v1.dto.request.ProductRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @PostMapping("/update/{productId}")
    public ProductResponse updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @GetMapping("/get/{productId}")
    public ProductResponse getProductById(@PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/list")
    public ProductResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/delete/{productId}")
    public ProductResponse deleteProduct(@PathVariable("productId") Long productId) {
        return productService.deleteProduct(productId);
    }

    @PostMapping("/book")
    public ProductResponse bookProduct(@RequestBody ProductBookingRequest productBookingRequest) {
        return productService.bookProduct(productBookingRequest);
    }
}
