package com.example.spotdengue.core.usecases;

import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class MakeReport {
    private final ReportRepository reportRepository;

    public MakeReport(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public MakeReportOutput execute(MakeReportInput input) throws Exception {
        Report report = Report.of(input.mobilePhone(), input.latitude(), input.longitude(), input.comments());
        for (String inputImage : input.images()) {
            report.addImage(inputImage);
        }
        reportRepository.save(report);
        return new MakeReportOutput(report.getID());
    }
}
