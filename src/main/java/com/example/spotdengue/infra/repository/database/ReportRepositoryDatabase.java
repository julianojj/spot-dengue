package com.example.spotdengue.infra.repository.database;

import com.example.spotdengue.core.domain.Report;
import com.example.spotdengue.core.domain.ReportRepository;

import java.sql.*;
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
            preparedStatement.setTimestamp(7, Timestamp.valueOf(report.getReportDate().atStartOfDay()));
            preparedStatement.execute();
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
                try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Image WHERE ReportID = ?")) {
                    preparedStatement.setString(1, report.getID());
                    ResultSet resultSetImage = preparedStatement.executeQuery();
                    while (resultSetImage.next()) {
                        report.addImage(resultSetImage.getString("URL"));
                    }
                }
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
            preparedStatement.setTimestamp(6, Timestamp.valueOf(report.getReportDate().atStartOfDay()));
            preparedStatement.setString(7, report.getID());
            preparedStatement.executeUpdate();
        }
    }

    private Report extractReportFromResultSet(ResultSet resultSet) throws SQLException {
        return Report.restore(
                resultSet.getString("ID"),
                resultSet.getString("MobilePhone"),
                resultSet.getDouble("Latitude"),
                resultSet.getDouble("Longitude"),
                resultSet.getString("Comments"),
                resultSet.getString("Status"),
                resultSet.getDate("CreatedAt").toLocalDate()
        );
    }
}
