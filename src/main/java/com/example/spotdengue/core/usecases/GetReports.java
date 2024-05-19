package com.example.spotdengue.core.usecases;

import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetReports {
    private final ReportRepository reportRepository;

    public GetReports(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<GetReportsOutput> execute() throws Exception {
        List<Report> reports =  this.reportRepository.findAll();
        List<GetReportsOutput> output = new ArrayList<>();
        for (Report report: reports) {
            output.add(new GetReportsOutput(
                    report.getID(),
                    report.getGeolocation().getLatitude(),
                    report.getGeolocation().getLongitude(),
                    report.getAddress().getCity(),
                    report.getAddress().getZipCode(),
                    report.getAddress().getState(),
                    report.getAddress().getStreet(),
                    report.getAddress().getStreetNumber(),
                    report.getAddress().getNeighborhood(),
                    report.getStatus(),
                    report.getComments(),
                    report.getReportDate(),
                    report.getImages())
            );
        }
        return output;
    }
}
