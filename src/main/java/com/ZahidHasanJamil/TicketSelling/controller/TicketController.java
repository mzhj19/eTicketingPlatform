package com.ZahidHasanJamil.TicketSelling.controller;

import com.ZahidHasanJamil.TicketSelling.constant.WebApiUrlConstants;
import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.dto.SearchReqDto;
import com.ZahidHasanJamil.TicketSelling.exception.NotValidException;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import com.ZahidHasanJamil.TicketSelling.service.EmailService;
import com.ZahidHasanJamil.TicketSelling.service.JwtService;
import com.ZahidHasanJamil.TicketSelling.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketService ticketService;
    @Autowired
    EmailService emailService;

    @PostMapping(WebApiUrlConstants.TICKET_SAVE_API)
    public ResponseEntity<String> saveNewTicket(@RequestBody @Valid NewTicketReqDto newTicketReqDto, BindingResult result, @RequestHeader(name = "Authorization") String token) {
        if (result.hasErrors()) {
            throw new NotValidException("PLEASE GIVE CORRECT DATA");
        }
        if (ticketService.saveNewTicket(newTicketReqDto, token)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESSFULLY ADDED THE TICKET");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLEASE GIVE CORRECT DATA");
    }

    @PutMapping(WebApiUrlConstants.TICKET_EDIT_API)
    public ResponseEntity<?> editTicket(@RequestHeader(name = "Authorization") String token, @PathVariable Long id, @RequestBody @Valid Ticket updateData, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException("PLEASE GIVE CORRECT DATA");
        }
        var resData = ticketService.editTicket(token, id, updateData);
        if (resData != null) {
            return ResponseEntity.ok("TICKET UPDATED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TICKET CAN'T BE UPDATED");
    }

    @PostMapping(WebApiUrlConstants.TICKET_SEARCH_API)
    public ResponseEntity<?> searchTicket(@RequestBody @Valid SearchReqDto searchReqDto) {
        if (searchReqDto.getToWhere() == null && searchReqDto.getFromWhere() == null && searchReqDto.getTicketType() == null && searchReqDto.getTDate() == null && searchReqDto.getPrice() == null) {
            throw new NotValidException("PLEASE GIVE CORRECT SEARCH DATA");
        }
        List<Ticket> searchResponse = ticketService.searchTicket(searchReqDto);
        return ResponseEntity.status(HttpStatus.FOUND).body(searchResponse);
    }

    @GetMapping(WebApiUrlConstants.TICKET_BUYABLE_API)
    public ResponseEntity<?> getBuyableTicket(@RequestHeader(name = "Authorization") String token) {

        List<Ticket> buyableTicket = ticketService.getBuyableTicket(token);

        if (buyableTicket != null) {
            return ResponseEntity.status(HttpStatus.OK).body(buyableTicket);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO DATA FOUND");
    }

    @GetMapping(WebApiUrlConstants.TICKET_BUY_API)
    public ResponseEntity<?> buyTicket(@RequestParam Long id, @RequestHeader(name = "Authorization") String token) {
        boolean bought = ticketService.buyTicket(id, token);
        if (bought) {
            return ResponseEntity.status(HttpStatus.OK).body("TICKET HAS BEEN BOUGHT SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("TICKET CAN'T BE BOUGHT RIGHT NOW.PLEASE TRY AGAIN");
    }

    @GetMapping(WebApiUrlConstants.TICKET_REFUND_API)
    public ResponseEntity<?> refundTicket(@RequestParam Long id) {
        if (ticketService.refundTicket(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("REFUND REQUEST HAS BEEN SENT");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR WHILE SENDING REQUEST");
    }

    @GetMapping(WebApiUrlConstants.TICKET_REFUND_FINALIZE_API)
    public ResponseEntity<?> finalizeRefund(@RequestParam Long id) {
        if (ticketService.finalizeRefund(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("REFUND HAS BEEN COMPLETED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("TICKET CAN'T BE REFUNDED RIGHT NOW");
    }
}
