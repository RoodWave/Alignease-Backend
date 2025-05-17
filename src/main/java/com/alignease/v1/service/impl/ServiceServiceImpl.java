package com.alignease.v1.service.impl;

import com.alignease.v1.repository.ServiceRepository;
import com.alignease.v1.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;
}
