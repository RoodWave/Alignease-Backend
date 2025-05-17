package com.alignease.v1.service.impl;

import com.alignease.v1.repository.ProductRepository;
import com.alignease.v1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
}
