package com.ZahidHasanJamil.TicketSelling.controller;

import com.ZahidHasanJamil.TicketSelling.dto.TicketBookedReqDto;
import com.ZahidHasanJamil.TicketSelling.model.User;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import com.ZahidHasanJamil.TicketSelling.service.AuthService;
import com.ZahidHasanJamil.TicketSelling.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping
    public void bookedTicket( @RequestHeader(name="Authorization") String token) {
        System.out.println(token.substring(7));
        System.out.println(jwtService.extractUsername(token.substring(7)));
        Optional<User> user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7)));
        System.out.println(user);
    }
}
