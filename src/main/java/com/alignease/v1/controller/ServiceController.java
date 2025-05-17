package com.alignease.v1.controller;

import com.alignease.v1.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;
}
