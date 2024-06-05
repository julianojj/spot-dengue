package com.example.spotdengue.core.usecases;

import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;
import com.example.spotdengue.core.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResolveReport {
    private final ReportRepository reportRepository;

    public ResolveReport(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void execute(ResolveReportInput input) throws Exception {
        Report existingReport = reportRepository.find(input.reportID());
        if (existingReport == null) {
            throw new NotFoundException("report not found");
        }
        existingReport.resolveReport(input.reason());
        reportRepository.update(existingReport);
    }
}

