package com.example.spotdengue.infra.repository.memory;



import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;

import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryMemory implements ReportRepository {
    private final List<Report> reports;

    public ReportRepositoryMemory() {
        this.reports = new ArrayList<>();
    }

    @Override
    public void save(Report report) {
        reports.add(report);
    }

    @Override
    public Report find(String reportID) {
        for (Report report : reports) {
            if (report.getID().equals(reportID)) {
                return report;
            }
        }
        return null;
    }

    @Override
    public List<Report> findAll() {
        return new ArrayList<>(reports);
    }

    @Override
    public void update(Report report) {
        int index = reports.indexOf(report);
        if (index != -1) {
            reports.set(index, report);
        }
    }
}
