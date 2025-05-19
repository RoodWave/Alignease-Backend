package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ReportRequest;
import com.alignease.v1.dto.request.ReportStatusRequest;
import com.alignease.v1.dto.response.ReportResponse;
import com.alignease.v1.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportsController {

    @Autowired
    ReportService reportService;

    @PostMapping("/add")
    public ReportResponse addReport(@RequestBody ReportRequest reportRequest) {
        return reportService.addReport(reportRequest);
    }

    @GetMapping("/list")
    public ReportResponse getReports() {
        return reportService.getReports();
    }

    @PutMapping("/status")
    public ReportResponse updateReportStatus(@RequestBody ReportStatusRequest request) {
        return reportService.updateReportStatus(request);
    }
}
