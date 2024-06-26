package com.example.spotdengue.core.domain;

import com.example.spotdengue.core.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    void testNewReport() {
        Address address = new Address("City", "12345678", "PR", "Street", 123, "Neighborhood");
        Report report = Report.of("(43) 99999-9999", -1, -1, address, "test");
        assertNotNull(report.getID());
        assertEquals("pending", report.getStatus());
    }

    @Test
    void testResolveReport() throws ValidationException {
        Address address = new Address("City", "12345678", "PR", "Street", 123, "Neighborhood");
        Report report = Report.of("(43) 99999-9999", -1, -1, address, "test");
        report.resolveReport("Test");
        assertEquals("resolved", report.getStatus());
    }

    @Test
    void testCancelReport() throws ValidationException {
        Address address = new Address("City", "12345678", "PR", "Street", 123, "Neighborhood");
        Report report = Report.of("(43) 99999-9999", -1, -1, address, "test");
        report.cancelReport("Test");
        assertEquals("canceled", report.getStatus());
    }
}
