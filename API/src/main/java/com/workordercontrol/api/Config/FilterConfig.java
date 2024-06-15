package com.workordercontrol.api.Config;

import com.workordercontrol.api.Domain.Service.AuthService;
import com.workordercontrol.api.Infra.Entity.Employee;
import com.workordercontrol.api.Infra.Repository.EmployeeRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class FilterConfig extends OncePerRequestFilter {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = (header == null) ? null : header.split(" ")[1];

        if(token != null){
            Employee employee = employeeRepository.findByEmail(authService.validateToken(token));

            UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(employee, null, employee.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
