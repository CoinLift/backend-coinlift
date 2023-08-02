package com.coinlift.backend.services.reports;

import com.coinlift.backend.dtos.reports.ReportRequestDto;

import java.util.UUID;

public interface ReportService {

    void sendPostReport(ReportRequestDto reportMsg, String type, UUID id);
}
