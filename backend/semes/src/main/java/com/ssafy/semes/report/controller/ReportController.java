package com.ssafy.semes.report.controller;

import com.ssafy.semes.common.ErrorCode;
import com.ssafy.semes.common.SuccessCode;
import com.ssafy.semes.common.dto.ApiResponse;
import com.ssafy.semes.report.model.QuestionDto;
import com.ssafy.semes.report.model.ReportListResponseDto;
import com.ssafy.semes.report.model.service.ReportService;
import com.ssafy.semes.util.Slack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;
    private Slack slack = new Slack();

    @PostMapping("/list")
    private ApiResponse<?> findReport( @RequestBody QuestionDto questionDto){
        log.info("Report FindReport Start");
        try {
            return ApiResponse.success(SuccessCode.READ_REPORT_LIST,reportService.findReport(questionDto));
        }catch (Exception e){
            log.error("Report FindReport Error : "+e.getMessage());
            return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @GetMapping("/detail/{wheelChcekId}")
    private  ApiResponse<?> findReportDetail(@PathVariable("wheelChcekId")long wheelChcekId){
        log.info("Report findReportDetail Start");
        try{
            return ApiResponse.success(SuccessCode.READ_REPORT_DETAIL, reportService.findReportDetail(wheelChcekId));
        }catch (Exception e){
            slack.send("Report findReportDetail Error : "+e.getMessage());
            log.error("Report findReportDetail Error : "+e.getMessage());
            return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}