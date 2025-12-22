package com.pma.reporter.controller;

import com.pma.reporter.dto.ReportSummaryDTO;
import com.pma.reporter.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    @GetMapping("/summary")
    public ResponseEntity<ReportSummaryDTO> getSummaryReport(
        @RequestParam(required = false) String date,
        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reportService.generateGeneralReport(date,token));
    }
}