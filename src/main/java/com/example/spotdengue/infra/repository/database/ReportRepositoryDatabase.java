package com.example.spotdengue.infra.repository.database;

import com.example.spotdengue.core.domain.Address;
import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryDatabase implements ReportRepository {
    private final Connection connection;

    public ReportRepositoryDatabase() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/report",
                "juliano",
                "12345678"
        );
    }

    @Override
    public void save(Report report) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Report(ID, MobilePhone, Latitude, Longitude, Comments, Status, CreatedAt) VALUES(?, ?, ?, ?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, report.getID());
            preparedStatement.setString(2, report.getMobilePhone());
            preparedStatement.setDouble(3, report.getGeolocation().getLatitude());
            preparedStatement.setDouble(4, report.getGeolocation().getLongitude());
            preparedStatement.setString(5, report.getComments());
            preparedStatement.setString(6, report.getStatus());
            preparedStatement.setTimestamp(7, Timestamp.from(report.getReportDate()));
            preparedStatement.execute();
        }

        try (PreparedStatement preparedStatementAddress = connection.prepareStatement(
                "INSERT INTO Address(ReportID, State, ZipCode, City, Street, StreetNumber, Neighborhood) VALUES(?, ?, ?, ?, ?, ?, ?)"
        )) {
            preparedStatementAddress.setString(1, report.getID());
            Address address = report.getAddress();
            preparedStatementAddress.setString(2, address.getState());
            preparedStatementAddress.setString(3, address.getZipCode());
            preparedStatementAddress.setString(4, address.getCity());
            preparedStatementAddress.setString(5, address.getStreet());
            preparedStatementAddress.setInt(6, address.getStreetNumber());
            preparedStatementAddress.setString(7, address.getNeighborhood());
            preparedStatementAddress.execute();
        }

        for (String image : report.getImages()) {
            try (PreparedStatement preparedStatementImage = connection.prepareStatement(
                    "INSERT INTO Image(ReportID, URL) VALUES(?, ?)"
            )) {
                preparedStatementImage.setString(1, report.getID());
                preparedStatementImage.setString(2, image);
                preparedStatementImage.execute();
            }
        }
    }

    @Override
    public Report find(String reportID) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM Report WHERE ID = ?"
        )) {
            preparedStatement.setString(1, reportID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractReportFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Report> findAll() throws SQLException {
        List<Report> reports = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Report");
            while (resultSet.next()) {
                Report report = extractReportFromResultSet(resultSet);
                reports.add(report);
            }
        }
        return reports;
    }

    @Override
    public void update(Report report) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Report SET MobilePhone = ?, Latitude = ?, Longitude = ?, Comments = ?, Status = ?, CreatedAt = ? WHERE ID = ?"
        )) {
            preparedStatement.setString(1, report.getMobilePhone());
            preparedStatement.setDouble(2, report.getGeolocation().getLatitude());
            preparedStatement.setDouble(3, report.getGeolocation().getLongitude());
            preparedStatement.setString(4, report.getComments());
            preparedStatement.setString(5, report.getStatus());
            preparedStatement.setTimestamp(6, Timestamp.from(report.getReportDate()));
            preparedStatement.setString(7, report.getID());
            preparedStatement.executeUpdate();
        }
    }

    private Report extractReportFromResultSet(ResultSet resultSet) throws SQLException {
        String reportID = resultSet.getString("ID");
        String mobilePhone = resultSet.getString("MobilePhone");
        double latitude = resultSet.getDouble("Latitude");
        double longitude = resultSet.getDouble("Longitude");
        Address address = extractAddressFromResultSet(reportID); // Assuming this method exists elsewhere
        List<String> images = extractImagesFromResultSet(reportID); // Assuming this method exists elsewhere
        String comments = resultSet.getString("Comments");
        String status = resultSet.getString("Status");
        Instant createdAt = resultSet.getTimestamp("CreatedAt").toInstant();
        Report report = Report.restore(reportID, mobilePhone, latitude, longitude, address, comments, status, createdAt);
        for (String image: images) {
            report.addImage(image);
        }
        return report;
    }

    private Address extractAddressFromResultSet(String reportID) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM Address WHERE ReportID = ?"
        )) {
            preparedStatement.setString(1, reportID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Address(
                        resultSet.getString("City"),
                        resultSet.getString("ZipCode"),
                        resultSet.getString("State"),
                        resultSet.getString("Street"),
                        resultSet.getInt("StreetNumber"),
                        resultSet.getString("Neighborhood")
                );
            }
        }
        return null;
    }

    private List<String> extractImagesFromResultSet(String reportID) throws SQLException {
        List<String> images = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM Image WHERE ReportID = ?"
        )) {
            preparedStatement.setString(1, reportID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    images.add(resultSet.getString("URL"));
                }
            }
        }
        return images;
    }
}
