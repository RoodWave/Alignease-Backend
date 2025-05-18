package com.alignease.v1.dto;

import com.alignease.v1.entity.Service;
import com.alignease.v1.entity.ServiceBooking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceBookingDTO {
    private ServiceBooking serviceBooking;
    private Service service;
}