package com.ZahidHasanJamil.TicketSelling.service;

import com.ZahidHasanJamil.TicketSelling.dto.EmailDetails;


public interface EmailService {
    String sendEmail(EmailDetails details);
}
