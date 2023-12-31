package com.ZahidHasanJamil.TicketSelling.service;

import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.dto.SearchReqDto;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Boolean saveNewTicket(NewTicketReqDto newTicketReqDto, String token);

    Optional<Ticket> editTicket(String token, Long id, Ticket updateData);

    List<Ticket> searchTicket(SearchReqDto searchReqDto);

    List<Ticket> getBuyableTicket(String token);

    boolean buyTicket(Long id, String token);

    boolean finalizeRefund(Long id);

    boolean refundTicket(Long id);
}
