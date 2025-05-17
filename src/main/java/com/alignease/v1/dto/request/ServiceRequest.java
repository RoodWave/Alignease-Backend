package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequest {
    private String name;
    private String description;
    private String cost;
    private String estimatedTime;
}
