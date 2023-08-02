package com.coinlift.backend.controllers;

import com.coinlift.backend.dtos.reports.ReportRequestDto;
import com.coinlift.backend.services.reports.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/reports")
@Tag(name = "Report Controller", description = "Endpoints for managing reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Send a report", description = "API to send a report for a post or comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/{type}/{id}")
    public ResponseEntity<String> sendReport(@RequestBody @Valid ReportRequestDto reportMsg,
                                             @PathVariable String type,
                                             @PathVariable UUID id) {
        reportService.sendPostReport(reportMsg, type, id);

        return ResponseEntity.ok("Message sent successfully!");
    }
}
