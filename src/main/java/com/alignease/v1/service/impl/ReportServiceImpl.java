package com.alignease.v1.service.impl;

import com.alignease.v1.dto.request.ReportRequest;
import com.alignease.v1.dto.request.ReportStatusRequest;
import com.alignease.v1.dto.response.ReportResponse;
import com.alignease.v1.entity.Report;
import com.alignease.v1.entity.ReportStatus;
import com.alignease.v1.repository.ReportRepository;
import com.alignease.v1.service.ReportService;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private Messages messages;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public ReportResponse addReport(ReportRequest reportRequest) {
        logger.info("Creating report starts");

        ReportResponse reportResponse = new ReportResponse();

        if(reportRequest != null && reportRequest.getIssueTitle() != null && reportRequest.getIssueDescription() != null) {
            Report map = modelMapper.map(reportRequest, Report.class);
            map.setReportStatus(ReportStatus.PENDING);
            Report save = reportRepository.save(map);

            logger.info("Creating report success");
            reportResponse.setReport(save);
            reportResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            reportResponse.setResponseCode(ResponseCodes.SUCCESS);
            reportResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORT_CREATE_SUCCESS, null));

            logger.info("Creating report ends with success");
            return reportResponse;
        } else {
            reportResponse.setStatus(RequestStatus.FAILURE.getStatus());
            reportResponse.setResponseCode(ResponseCodes.REPORT_CREATE_FAILURE);
            reportResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORT_CREATE_FAILURE, null));

            logger.info("Creating report ends with failure");
            return reportResponse;
        }
    }

    @Override
    public ReportResponse getReports() {
        logger.info("Fetch reports starts");

        ReportResponse reportResponse = new ReportResponse();

        try{
            List<Report> all = reportRepository.findAll();
            logger.info("Creating report success");
            reportResponse.setReportList(all);
            reportResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            reportResponse.setResponseCode(ResponseCodes.SUCCESS);
            reportResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORT_CREATE_SUCCESS, null));

            logger.info("Fetch reports success");
            return reportResponse;
        } catch (Exception e){
            reportResponse.setStatus(RequestStatus.FAILURE.getStatus());
            reportResponse.setResponseCode(ResponseCodes.REPORTS_FETCH_FAILURE);
            reportResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORTS_FETCH_FAILURE, null));

            logger.info("Creating reports failure");
            return reportResponse;
        }
    }

    @Override
    @Transactional
    public ReportResponse updateReportStatus(ReportStatusRequest request) {
        logger.info("Updating report status for report associated with report ID: {}", request.getReportId());

        ReportResponse response = new ReportResponse();

        Optional<Report> reportOpt = reportRepository.findById(request.getReportId());
        if (reportOpt.isEmpty()) {
            logger.error("Report not found with ID: {}", request.getReportId());
            response.setStatus(RequestStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.REPORT_NOT_FOUND);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORT_NOT_FOUND, null));
            return response;
        }

        try {
            Report report = reportOpt.get();
            report.setReportStatus(request.getStatus());
            Report updatedReport = reportRepository.save(report);

            response.setReport(updatedReport);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORTS_STATUS_UPDATE_SUCCESS, null));

            logger.info("Report status updated successfully for report ID: {}", request.getReportId());

        } catch (Exception e) {
            logger.error("Error updating report status: {}", e.getMessage(), e);
            response.setStatus(RequestStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.REPORTS_STATUS_UPDATE_FAILURE);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.REPORTS_STATUS_UPDATE_FAILURE, null));
        }

        return response;
    }
}
