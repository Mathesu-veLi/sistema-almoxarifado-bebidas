package com.senai.bebidas.controller;

import com.senai.bebidas.dto.LoginRequest;
import com.senai.bebidas.dto.LoginResponse;
import com.senai.bebidas.dto.MensagemErroDTO;
import com.senai.bebidas.security.JwtUtil;
import com.senai.bebidas.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha()));
        } catch (AuthenticationException e) {
            // Credenciais invalidas ou usuario inativo: 401 com corpo do contrato.
            MensagemErroDTO erro = new MensagemErroDTO(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Credenciais inválidas",
                    OffsetDateTime.now().toString());
            return new ResponseEntity<>(erro, HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt, "Bearer", 3600L));
    }
}
