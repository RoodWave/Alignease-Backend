package com.alignease.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String email;

    @JsonIgnore
    private String password;

    private String isDeleted;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ServiceBooking> serviceBookings;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ProductBooking> productBookings;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
