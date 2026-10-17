package com.project.booknes.controller;

import com.project.booknes.dto.AuthResponseDTO;
import com.project.booknes.dto.LoginRequestDTO;
import com.project.booknes.dto.RegisterRequestDTO;
import com.project.booknes.service.AuthService;
import com.project.booknes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody
                                                        @Valid RegisterRequestDTO requestDTO) {

        return new ResponseEntity<>(authService.register(requestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid
                                                 LoginRequestDTO loginRequest) {

        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }
}
