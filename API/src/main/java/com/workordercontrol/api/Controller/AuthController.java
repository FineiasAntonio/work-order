package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.AuthService;
import com.workordercontrol.api.Exception.CustomExceptions.ForbiddenException;
import com.workordercontrol.api.Infra.DTO.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        var token = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Bad credentials");
        }

        String jwtToken = authService.generateToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

}
