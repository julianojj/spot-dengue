package com.example.spotdengue.core.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    void testNewReport() {
        Report report = Report.of("(43) 99999-9999", -1, -1, "test");
        assertNotNull(report.getID());
        assertEquals("pending", report.getStatus());
    }

    @Test
    void testResolveReport() {
        Report report = Report.of("(43) 99999-9999", -1, -1, "test");
        report.resolveReport();
        assertEquals("resolved", report.getStatus());
    }

    @Test
    void testCancelReport() {
        Report report = Report.of("(43) 99999-9999", -1, -1, "test");
        report.cancelReport();
        assertEquals("canceled", report.getStatus());
    }
}
