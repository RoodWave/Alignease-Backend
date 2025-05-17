package com.alignease.v1.service;

import com.alignease.v1.dto.request.ProductRequest;
import com.alignease.v1.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long productId, ProductRequest productRequest);
    ProductResponse getProductById(Long productId);
    ProductResponse getAllProducts();
    ProductResponse deleteProduct(Long productId);
}
