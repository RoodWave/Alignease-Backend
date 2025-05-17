package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ProductBookingRequest;
import com.alignease.v1.dto.request.ProductRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.service.ProductService;
import com.alignease.v1.utils.ResponseCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse addProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequest productRequest = objectMapper.readValue(productJson, ProductRequest.class);
            productRequest.setImageFile(imageFile);
            return productService.addProduct(productRequest);
        } catch (JsonProcessingException e) {
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    "Invalid product request format");
        }
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
