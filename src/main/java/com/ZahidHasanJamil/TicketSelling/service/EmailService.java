package com.ZahidHasanJamil.TicketSelling.service;

import com.ZahidHasanJamil.TicketSelling.dto.EmailDetails;


public interface EmailService {
    boolean sendEmail(EmailDetails emailDetails);
}
