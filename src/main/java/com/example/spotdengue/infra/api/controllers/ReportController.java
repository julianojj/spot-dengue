package com.example.spotdengue.infra.api.controllers;

import com.example.spotdengue.core.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ReportController {
    @Autowired
    private MakeReport makeReport;
    @Autowired
    private ResolveReport resolveReport;
    @Autowired
    private CancelReport cancelReport;
    @Autowired
    private GetReports getReports;

    @PostMapping("/make_report")
    public ResponseEntity<MakeReportOutput> makeReport(@RequestBody MakeReportInput input) throws Exception {
        MakeReportOutput output = this.makeReport.execute(input);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

    @PostMapping("/resolve_report")
    public ResponseEntity<Object> resolveReport(@RequestBody ResolveReportInput input) throws Exception {
        this.resolveReport.execute(input);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/cancel_report")
    public ResponseEntity<Object> cancelReport(@RequestBody CancelReportInput input) throws Exception {
        this.cancelReport.execute(input);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get_all_reports")
    public ResponseEntity<List<GetReportsOutput>> getAllReports() throws Exception {
        List<GetReportsOutput> output = this.getReports.execute();
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
