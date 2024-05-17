package com.example.spotdengue.core.domain;


import java.util.List;

public interface ReportRepository {
    void save(Report report) throws Exception;
    Report find(String reportID) throws Exception;
    List<Report> findAll() throws Exception;
    void update(Report report) throws Exception;
}
