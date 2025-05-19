package com.alignease.v1.service;

import com.alignease.v1.dto.request.ReportRequest;
import com.alignease.v1.dto.request.ReportStatusRequest;
import com.alignease.v1.dto.response.ReportResponse;

public interface ReportService {
    ReportResponse addReport(ReportRequest reportRequest);
    ReportResponse getReports();
    ReportResponse updateReportStatus(ReportStatusRequest request);
}
