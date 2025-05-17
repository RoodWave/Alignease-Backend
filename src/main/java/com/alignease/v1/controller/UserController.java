package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ProductBookingHistoryRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/history/product-booking")
    public ProductResponse getUserBookingHistory(
            @RequestBody ProductBookingHistoryRequest request) {
        return userService.getUserBookingHistory(request);
    }
}
