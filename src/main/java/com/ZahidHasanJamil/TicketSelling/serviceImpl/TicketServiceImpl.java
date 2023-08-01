package com.ZahidHasanJamil.TicketSelling.serviceImpl;

import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import com.ZahidHasanJamil.TicketSelling.model.User;
import com.ZahidHasanJamil.TicketSelling.repository.TicketRepository;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import com.ZahidHasanJamil.TicketSelling.service.JwtService;
import com.ZahidHasanJamil.TicketSelling.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Override
    public Boolean saveNewTicket(NewTicketReqDto newTicketReqDto, String token) {
        Optional<User> user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7)));

        Ticket ticket = new Ticket();
        ticket.setTicketType(newTicketReqDto.getTicketType());
        ticket.setSeller(user.get().getFirstName() + user.get().getLastName());
        ticket.setSellerEmail(user.get().getEmail());
        ticket.setBuyer("");
        ticket.setDate(newTicketReqDto.getDate());
        ticket.setFromWhere(newTicketReqDto.getFromWhere());
        ticket.setToWhere(newTicketReqDto.getToWhere());
        ticket.setPrice(newTicketReqDto.getPrice());

        var successfullInsert = ticketRepository.save(ticket);
        if(successfullInsert != null)   return true;

        return false;
    }
}
