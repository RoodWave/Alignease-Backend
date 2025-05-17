package com.alignease.v1.dto.request;

import com.alignease.v1.entity.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBookingHistoryRequest {
    private Long userId;
    private BookingStatus bookingStatus;
}
