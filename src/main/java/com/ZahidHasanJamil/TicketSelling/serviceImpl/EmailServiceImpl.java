package com.ZahidHasanJamil.TicketSelling.serviceImpl;

import com.ZahidHasanJamil.TicketSelling.dto.EmailDetails;
import com.ZahidHasanJamil.TicketSelling.repository.TicketRepository;
import com.ZahidHasanJamil.TicketSelling.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    TicketRepository ticketRepository;

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public boolean sendEmail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
