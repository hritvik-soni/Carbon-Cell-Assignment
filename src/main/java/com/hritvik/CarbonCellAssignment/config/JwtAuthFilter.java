package com.hritvik.CarbonCellAssignment.config;


import com.hritvik.CarbonCellAssignment.exceptions.GlobalExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtService;
    @Autowired
    private UserInfoUserDetailsService userDetailsService;
    @Autowired
    private SecurityBean securityBean;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("jwt filter called");
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);

        // Skip JWT processing for permitted paths
        if (shouldSkipJwtProcessing(request)) {
            log.info("Skipping JWT processing");
            filterChain.doFilter(request, response);
            return;
        }
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Authorization header found");
            String token = authorizationHeader.substring(7);// Remove "Bearer " prefix
            if (JwtUtil.isTokenInvalidated(token)) {
                log.error("Token is invalidated please login again and use new token");
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token is invalidated please login again and use new token");
                throw new GlobalExceptionHandler.JwtValidationException("Token is invalidated please login again and use new token");
            }
            if (jwtService.validateToken(token)) {
                log.info("Token is valid");
                String username = jwtService.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                log.error("Invalid JWT token");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                throw new GlobalExceptionHandler.JwtValidationException("Invalid JWT token");
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipJwtProcessing(HttpServletRequest request) {
        String path = request.getRequestURI();
        return Stream.of("/api/v1/auth/login", "/api/v1/users/register", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .anyMatch(path::startsWith);
    }
}

