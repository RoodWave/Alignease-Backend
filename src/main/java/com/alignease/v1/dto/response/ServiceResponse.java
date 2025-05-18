package com.alignease.v1.dto.response;

import com.alignease.v1.dto.ServiceBookingDTO;
import com.alignease.v1.entity.Service;
import com.alignease.v1.entity.ServiceBooking;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse extends Response{
    private Service service;
    private List<Service> services;
    private List<ServiceBooking> serviceBookings;
    private List<ServiceBookingDTO> serviceBookingsWithDetails;
}
