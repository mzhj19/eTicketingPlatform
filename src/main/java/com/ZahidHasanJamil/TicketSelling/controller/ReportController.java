package com.ZahidHasanJamil.TicketSelling.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @PostMapping
    public String report() {

        return "Hello from report";
    }
}
