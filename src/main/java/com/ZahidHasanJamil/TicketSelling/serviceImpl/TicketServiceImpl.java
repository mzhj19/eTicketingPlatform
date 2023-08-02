package com.ZahidHasanJamil.TicketSelling.serviceImpl;

import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.dto.SearchReqDto;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import com.ZahidHasanJamil.TicketSelling.model.User;
import com.ZahidHasanJamil.TicketSelling.repository.TicketRepository;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import com.ZahidHasanJamil.TicketSelling.service.JwtService;
import com.ZahidHasanJamil.TicketSelling.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
        Specification<Ticket> spec = Specification.where((root, query, criteriaBuilder) -> null);

        if (searchReqDto.getFromWhere() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fromwhere"), searchReqDto.getFromWhere()));
        }

        if (searchReqDto.getToWhere() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("towhere"), searchReqDto.getToWhere()));
        }

        if (searchReqDto.getTicketType() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ticketType"), searchReqDto.getTicketType()));
        }

        if (searchReqDto.getDate() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), searchReqDto.getDate()));
        }

        if (searchReqDto.getPrice() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), searchReqDto.getPrice()));
        }
        return ticketRepository.findAll((Sort) spec);
    }
}
