package com.ssafy.semes.dashboard.controller;


import com.ssafy.semes.common.ErrorCode;
import com.ssafy.semes.common.SuccessCode;
import com.ssafy.semes.common.dto.ApiResponse;
import com.ssafy.semes.dashboard.model.DashboardMainResponseDto;
import com.ssafy.semes.dashboard.model.OHTCheckResponseDto;
import com.ssafy.semes.dashboard.model.SseEmitters;
import com.ssafy.semes.dashboard.model.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController {
    private final SseEmitters sseEmitters;
    @Autowired
    private DashboardService dashboardService;

    public DashboardController(SseEmitters sseEmitters) {
        this.sseEmitters = sseEmitters;
    }
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        log.info("DashBoard Start");
        List<OHTCheckResponseDto> list = null;
        try {
            list = dashboardService.findAllCheck();
            SseEmitter emitter = new SseEmitter();
            sseEmitters.add(emitter);
            emitter.send(SseEmitter.event()
                    .name("dashboard")
                    .data(list));

            return ResponseEntity.ok(emitter);
        }catch (Exception e){
            log.error("DashBoard Error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/main/{oht-check-id}")
    public ApiResponse<?> showMain(@PathVariable("oht-check-id")long ohtCheckId){
        log.info("DashBoard ShowMain Start");
        List<DashboardMainResponseDto> list= null;
        try {
            list = dashboardService.findAllMain(ohtCheckId);
            log.info("DashboardMainResponseDtos : " + list);
            return ApiResponse.success(SuccessCode.READ_DASHBOARD_MAIN,list);
        }catch (Exception e){
            log.error("DashBoard ShowMain Error : " + e.getMessage());
            return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}