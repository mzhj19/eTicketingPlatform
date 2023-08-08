package com.ZahidHasanJamil.TicketSelling.service;

public interface ReportService {
    byte[] generateReport(String startDate, String endDate) throws Exception;
}
