package com.alignease.v1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reporterName;
    private String reporterEmail;
    private String reporterContact;
    private String service;
    private String issueTitle;
    private String issueDescription;
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

}
