package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {
    private String reporterName;
    private String reporterEmail;
    private String reporterContact;
    private String service;
    private String issueTitle;
    private String issueDescription;
}
