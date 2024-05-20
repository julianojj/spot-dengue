package com.example.spotdengue.core.usecases;


import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;
import com.example.spotdengue.infra.repository.memory.ReportRepositoryMemory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MakeReportTest {
    @Test
    public void NewMakeReport() throws Exception {
        ReportRepository reportRepository = new ReportRepositoryMemory();
        MakeReport makeReport = new MakeReport(reportRepository);
        MakeReportInput input = new MakeReportInput(
                "(43) 99999-9999",
                -1,
                -1,
                "City",
                "12345678",
                "ST",
                "Street",
                123,
                "Neighborhood",
                new ArrayList<>(),
                "Test"
        );
        MakeReportOutput output = makeReport.execute(input);
        Report report = reportRepository.find(output.reportID());
        assertEquals("pending", report.getStatus());
    }

    @Test
    public void NewCancelReport() throws Exception {
        ReportRepository reportRepository = new ReportRepositoryMemory();
        MakeReport makeReport = new MakeReport(reportRepository);
        CancelReport cancelReport = new CancelReport(reportRepository);
        MakeReportInput input = new MakeReportInput(
                "(43) 99999-9999",
                -1,
                -1,
                "City",
                "12345678",
                "ST",
                "Street",
                123,
                "Neighborhood",
                new ArrayList<>(),
                "Test"
        );
        MakeReportOutput output = makeReport.execute(input);
        CancelReportInput cancelReportInput = new CancelReportInput(output.reportID());
        cancelReport.execute(cancelReportInput);
        Report report = reportRepository.find(output.reportID());
        assertEquals("canceled", report.getStatus());
    }

    @Test
    public void NewResolveReport() throws Exception {
        ReportRepository reportRepository = new ReportRepositoryMemory();
        MakeReport makeReport = new MakeReport(reportRepository);
        ResolveReport resolveReport = new ResolveReport(reportRepository);
        MakeReportInput input = new MakeReportInput(
                "(43) 99999-9999",
                -1,
                -1,
                "City",
                "12345678",
                "ST",
                "Street",
                123,
                "Neighborhood",
                new ArrayList<>(),
                "Test"
        );
        MakeReportOutput output = makeReport.execute(input);
        ResolveReportInput resolveReportInput = new ResolveReportInput(output.reportID());
        resolveReport.execute(resolveReportInput);
        Report report = reportRepository.find(output.reportID());
        assertEquals("resolved", report.getStatus());
    }
}
