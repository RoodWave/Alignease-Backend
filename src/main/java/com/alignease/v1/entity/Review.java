package com.alignease.v1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String title;
    private String content;

    @OneToOne
    @JoinColumn(name = "service_booking_id")
    private ServiceBooking serviceBooking;

    @OneToOne
    @JoinColumn(name = "product_booking_id")
    private ProductBooking productBooking;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;
}
