package com.alignease.v1.dto.response;

import com.alignease.v1.entity.Report;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse extends Response{
    private Report report;
    private List<Report> reportList;
}
