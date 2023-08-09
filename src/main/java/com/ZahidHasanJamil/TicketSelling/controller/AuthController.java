package com.ZahidHasanJamil.TicketSelling.controller;

import com.ZahidHasanJamil.TicketSelling.constant.WebApiUrlConstants;
import com.ZahidHasanJamil.TicketSelling.dto.AuthResponseDto;
import com.ZahidHasanJamil.TicketSelling.dto.LoginRequestDto;
import com.ZahidHasanJamil.TicketSelling.dto.RegisterRequestDto;
import com.ZahidHasanJamil.TicketSelling.exception.NotValidException;
import com.ZahidHasanJamil.TicketSelling.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(WebApiUrlConstants.USER_LOGIN_API)
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto registerRequestDto) {
        //return ResponseEntity.ok(authService.login(registerRequestDto));

        try {
            System.out.println(registerRequestDto.toString() + '\n');
            AuthResponseDto response = authService.login(registerRequestDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(WebApiUrlConstants.USER_REGISTER_API)
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto, BindingResult bindingResult) throws NotValidException {
        System.out.println(registerRequestDto);
        if (bindingResult.hasErrors()) {
            throw new NotValidException("EMAIL IN REQUIRED. PLEASE GIVE A VALID EMAIL");
        }
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }
}
