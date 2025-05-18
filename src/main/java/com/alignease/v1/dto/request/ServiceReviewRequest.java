package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceReviewRequest {
    private String title;
    private String content;
    private String rating;
    private Long serviceBookingId;
}
