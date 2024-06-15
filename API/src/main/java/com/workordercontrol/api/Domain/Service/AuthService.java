package com.workordercontrol.api.Domain.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.workordercontrol.api.Infra.DTO.LoginRequest;
import com.workordercontrol.api.Infra.Entity.Employee;
import com.workordercontrol.api.Infra.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class AuthService implements UserDetailsService {

    @Value("${jwt.secret")
    private Algorithm algorithm;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email);
    }

    public String generateToken(LoginRequest request) {
        Employee user = employeeRepository.findByEmail(request.email());

        Map<String, Object> claims = Map.of(
                "employeeId", user.getEmployeeId(),
                "employeeName", user.getName(),
                "employeeEmail", user.getEmail(),
                "employeeRole", user.getAuthorities()
        );

        return JWT.create()
                .withIssuer("API-AUTH")
                .withSubject(user.getEmail())
                .withClaim("employeeDetails", claims)
                .withExpiresAt(
                        LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.UTC)
                ).sign(algorithm);
    }

    public String validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("API-AUTH")
                .build()
                .verify(token)
                .getSubject();
    }

}
