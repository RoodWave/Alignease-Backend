package com.alignease.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ServiceRequest {
    private String name;
    private String description;
    private String cost;
    private String estimatedTime;

    @JsonIgnore
    private MultipartFile imageFile;
}
