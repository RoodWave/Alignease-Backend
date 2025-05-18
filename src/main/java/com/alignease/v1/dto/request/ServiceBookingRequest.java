package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ServiceBookingRequest {
    private Long serviceId;
    private Long userId;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
}
