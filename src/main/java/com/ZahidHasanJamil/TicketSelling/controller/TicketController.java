package com.ZahidHasanJamil.TicketSelling.controller;

import com.ZahidHasanJamil.TicketSelling.dto.NewTicketReqDto;
import com.ZahidHasanJamil.TicketSelling.exception.NotValidException;
import com.ZahidHasanJamil.TicketSelling.model.Ticket;
import com.ZahidHasanJamil.TicketSelling.model.User;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import com.ZahidHasanJamil.TicketSelling.service.JwtService;
import com.ZahidHasanJamil.TicketSelling.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketService ticketService;

    @PostMapping
    public String bookedTicket(@RequestHeader(name = "Authorization") String token) {
        System.out.println(token.substring(7));
        System.out.println(jwtService.extractUsername(token.substring(7)));
        Optional<User> user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7)));
        System.out.println(user);
        return "Hello from bookedTicket";
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveNewTicket(@RequestBody @Valid NewTicketReqDto newTicketReqDto, BindingResult result, @RequestHeader(name = "Authorization") String token) {
        if (result.hasErrors()) {
            throw new NotValidException("PLEASE GIVE CORRECT DATA");
        }
        if (ticketService.saveNewTicket(newTicketReqDto, token)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESSFULLY ADDED THE TICKET");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLEASE GIVE CORRECT DATA");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editTicket(@PathVariable Long id, @RequestBody @Valid Ticket updateData, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException("PLEASE GIVE CORRECT DATA");
        }
        var resData = ticketService.editTicket(id, updateData);
        if (resData != null) {
            return ResponseEntity.ok("TICKET UPDATED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TICKET CAN'T BE UPDATED");
    }

}

