package com.example.spotdengue;

import com.example.spotdengue.core.domain.FileRepository;
import com.example.spotdengue.core.domain.ReportRepository;
import com.example.spotdengue.core.usecases.*;
import com.example.spotdengue.infra.adapters.S3;
import com.example.spotdengue.infra.repository.database.ReportRepositoryDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication()
public class SpotDengueApplication {
	private final MakeReport makeReport;
	private final ResolveReport resolveReport;
	private final CancelReport cancelReport;
	private final GetReports getReports;
	private final UploadFile uploadFile;
	private final GetFile getFile;
	private final DeleteFile deleteFile;

	public SpotDengueApplication() throws SQLException {
		ReportRepository reportRepository = new ReportRepositoryDatabase();
		FileRepository fileRepository = new S3();

		this.makeReport = new MakeReport(reportRepository);
		this.resolveReport = new ResolveReport(reportRepository);
		this.cancelReport = new CancelReport(reportRepository);
		this.getReports = new GetReports(reportRepository);
		this.uploadFile = new UploadFile(fileRepository);
		this.getFile = new GetFile(fileRepository);
		this.deleteFile = new DeleteFile(fileRepository);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpotDengueApplication.class, args);
	}

	@Bean
	public MakeReport makeReport() {
		return this.makeReport;
	}

	@Bean
	public ResolveReport resolveReport() {
		return this.resolveReport;
	}

	@Bean CancelReport cancelReport() {
		return this.cancelReport;
	}

	@Bean
	public GetReports getReports() {
		return this.getReports;
	}

	@Bean
	public UploadFile uploadFile() {
		return this.uploadFile;
	}

	@Bean
	public GetFile getFile() {
		return this.getFile;
	}

	@Bean
	public DeleteFile deleteFile() {
		return this.deleteFile;
	}
}
