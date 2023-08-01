package com.ZahidHasanJamil.TicketSelling.service;

import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;

import java.util.Optional;

public interface TicketService {
    Boolean saveNewTicket(NewTicketReqDto newTicketReqDto, String token);

    Optional<Ticket> editTicket(Long id, Ticket updateData);
}
