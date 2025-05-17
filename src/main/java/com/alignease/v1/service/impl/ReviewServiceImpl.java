package com.alignease.v1.service.impl;

import com.alignease.v1.repository.ReviewRepository;
import com.alignease.v1.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
}
