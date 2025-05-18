package com.alignease.v1.dto.response;

import com.alignease.v1.entity.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewFilterResponse extends Response{
    List<Review> reviews;
}
