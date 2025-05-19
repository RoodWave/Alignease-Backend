package com.alignease.v1.dto.request;

import com.alignease.v1.entity.ReportStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportStatusRequest {
    private Long reportId;
    private ReportStatus status;
}
