package com.project.booknes.service;

import com.project.booknes.dto.AuthResponseDTO;
import com.project.booknes.dto.LoginRequestDTO;
import com.project.booknes.dto.RegisterRequestDTO;
import com.project.booknes.exception.ResourceDuplicationException;
import com.project.booknes.model.User;
import com.project.booknes.repository.UserRepository;
import com.project.booknes.security.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }


    public AuthResponseDTO register(RegisterRequestDTO dto) {

        if(userRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceDuplicationException("Username already taken");
        }

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceDuplicationException("Email already taken");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);


        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(), requestDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return new AuthResponseDTO(token);
    }
}
