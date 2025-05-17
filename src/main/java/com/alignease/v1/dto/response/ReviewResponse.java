package com.alignease.v1.dto.response;

import com.alignease.v1.entity.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse extends Response{
    private Long reviewId;
    private String title;
    private String content;
    private ReviewStatus reviewStatus;
    private Long bookingId;
    private boolean isServiceBooking;
}
