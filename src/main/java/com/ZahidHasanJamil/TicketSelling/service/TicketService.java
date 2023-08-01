package com.ZahidHasanJamil.TicketSelling.service;

import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;

public interface TicketService {
    Boolean saveNewTicket(NewTicketReqDto newTicketReqDto, String token);
}
