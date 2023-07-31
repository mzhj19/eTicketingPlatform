package com.ZahidHasanJamil.TicketSelling.service;

import com.ZahidHasanJamil.TicketSelling.exception.UserAlreadyExistsException;
import com.ZahidHasanJamil.TicketSelling.model.*;
import com.ZahidHasanJamil.TicketSelling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println(user);
        var jwt = jwtService.generateToken(user);
        return AuthResponseDto.builder().token(jwt).build();
    }

    public AuthResponseDto register(RegisterRequestDto request) throws UserAlreadyExistsException {

        var tempUser = userRepository.findByEmail(request.getEmail());
        if (!tempUser.isEmpty()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
        }

        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).mobile(request.getMobile()).role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthResponseDto.builder().token(jwt).build();
    }

}
