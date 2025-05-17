package com.alignease.v1.dto.request;

import com.alignease.v1.entity.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewStatusRequest {
    private Long reviewId;
    private ReviewStatus status;
}
