package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ProductBookingHistoryRequest;
import com.alignease.v1.dto.request.ServiceBookingHistoryRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.dto.response.ServiceResponse;
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
    public ProductResponse getUserProductBookingHistory(
            @RequestBody ProductBookingHistoryRequest request) {
        return userService.getUserProductBookingHistory(request);
    }

    @PostMapping("/history/service-booking")
    public ServiceResponse getUserServiceBookingHistory(
            @RequestBody ServiceBookingHistoryRequest request) {
        return userService.getUserServiceBookingHistory(request);
    }
}
