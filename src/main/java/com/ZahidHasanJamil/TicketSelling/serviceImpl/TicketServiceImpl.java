package com.ZahidHasanJamil.TicketSelling.serviceImpl;

import com.ZahidHasanJamil.TicketSelling.dto.EmailDetails;
import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.dto.SearchReqDto;
import com.ZahidHasanJamil.TicketSelling.model.SoldTicket;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import com.ZahidHasanJamil.TicketSelling.model.User;
import com.ZahidHasanJamil.TicketSelling.repository.SoldTicketRepository;
import com.ZahidHasanJamil.TicketSelling.repository.TicketRepository;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import com.ZahidHasanJamil.TicketSelling.service.EmailService;
import com.ZahidHasanJamil.TicketSelling.service.JwtService;
import com.ZahidHasanJamil.TicketSelling.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    @Autowired
    SoldTicketRepository soldTicketRepository;
    @Autowired
    EmailService emailService;

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
        ticket.setSellStatus(false);

        var successfullInsert = ticketRepository.save(ticket);
        if (successfullInsert != null) return true;

        return false;
    }

    @Override
    public Optional<Ticket> editTicket(Long id, Ticket updateData) {
        Ticket previousTicket = ticketRepository.findById(id).orElseThrow(null);
        if (previousTicket == null) return null;

        previousTicket.setTicketType(updateData.getTicketType());
        previousTicket.setSeller(previousTicket.getSeller());
        previousTicket.setSellerEmail(previousTicket.getSellerEmail());
        previousTicket.setDate(updateData.getDate());
        previousTicket.setFromWhere(updateData.getFromWhere());
        previousTicket.setToWhere(updateData.getToWhere());
        previousTicket.setPrice(updateData.getPrice());
        previousTicket.setSellStatus(updateData.isSellStatus());

        var successfullUpdated = ticketRepository.save(previousTicket);
        if (successfullUpdated != null) {
            return Optional.of(successfullUpdated);
        }
        return null;
    }

    @Override
    public List<Ticket> searchTicket(SearchReqDto searchReqDto) {
        List<Ticket> searchRes = ticketRepository.findBySearchReqDtoObject(searchReqDto.getFromWhere(), searchReqDto.getToWhere(), searchReqDto.getTicketType(), searchReqDto.getTDate(), searchReqDto.getPrice());
        return searchRes;
    }

    @Override
    public List<Ticket> getBuyableTicket(String token) {
        String sellerEmail = userRepository.findByEmail(jwtService.extractUsername(token.substring(7))).get().getUsername();
        return ticketRepository.findTicketBySellerEmailNotAndSellStatusFalse(sellerEmail);
    }

    @Override
    public boolean buyTicket(Long id, String token) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        User user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7))).orElse(null);

        if (ticket != null) {
            ticket.setBuyer(user.getFirstName() + ' ' + user.getLastName());
            ticket.setSellStatus(true);
            ticketRepository.save(ticket);

            SoldTicket soldTicket = new SoldTicket();
            soldTicket.setTicketType(ticket.getTicketType());
            soldTicket.setSoldDate(LocalDateTime.now());
            soldTicket.setAmountInTk(ticket.getPrice());
            soldTicket.setTicket(ticket);
            soldTicketRepository.save(soldTicket);

            return true;
        }
        return false;
    }

    @Override
    public boolean refundTicket(Long id) {
        EmailDetails emailDetails = new EmailDetails();
        Ticket ticket = ticketRepository.findTicketById(id);
        if (ticket == null) return false;
        String recipient = ticket.getSellerEmail();

        String textBody = "This request message is from '" + ticket.getBuyer() + "' for refunding the ticket id: " + id + ".\n" + "Click here to accept the request\n" + "http://localhost:8080/api/v1/ticket/refund-finalize?id=" + id;
        emailDetails.setRecipient(recipient);
        emailDetails.setSubject("TICKET REFUND REQUEST");
        emailDetails.setMsgBody(textBody);

        return emailService.sendEmail(emailDetails);
    }

    @Override
    public boolean finalizeRefund(Long id) {
        var soldTicket = soldTicketRepository.findByTicketId(id);
        if (soldTicket != null) {
            Long soldId = soldTicket.getId();
            soldTicketRepository.deleteById(soldId);
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null) {
                ticket.setBuyer("");
                ticket.setSellStatus(false);
                ticketRepository.save(ticket);
            }
        }
        return true;
    }
}
