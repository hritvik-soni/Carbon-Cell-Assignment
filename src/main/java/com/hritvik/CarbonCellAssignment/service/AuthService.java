package com.hritvik.CarbonCellAssignment.service;


import com.hritvik.CarbonCellAssignment.config.JwtUtil;
import com.hritvik.CarbonCellAssignment.dto.UserLoginRequestDto;
import com.hritvik.CarbonCellAssignment.dto.UserLoginResponseDto;
import com.hritvik.CarbonCellAssignment.utils.Response;
import com.hritvik.CarbonCellAssignment.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ResponseUtil responseUtil;

    public Response loginUser(UserLoginRequestDto userLoginRequestDto) {
        //  Validate user input
        if (userLoginRequestDto == null || StringUtils.isBlank(userLoginRequestDto.getEmail()) || StringUtils.isBlank(userLoginRequestDto.getPassword())) {
            responseUtil.unauthorizedResponse("Email and password cannot be null or empty");
        }

        // Authenticate user with the provided username and password
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
        } catch (AuthenticationException e) {
            responseUtil.unauthorizedResponse("Invalid username or password");
        }

        // Set the authenticated user's security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token for the authenticated user
        String jwt = jwtUtil.generateToken(authentication.getName());

        // Return the authentication response with the generated JWT token
        return responseUtil.successResponse("Login Successful", new UserLoginResponseDto(jwt));
    }

    public Response logoutUser(String token) {

        try {
            // Validate the token
            log.info("Token Invalidation Started");
            if (StringUtils.isBlank(token)) {
                log.error("Token cannot be null or empty");
                responseUtil.badRequestResponse("Token cannot be null or empty");
            }

            // Invalidate the token
            log.info("Invalidating token: {}", token);
            if (token.contains("Bearer ")) {
                token = token.substring(7);
            }
            JwtUtil.invalidateToken(token);
            log.info("Token invalidated successfully");
            return responseUtil.successResponse("Logout successful", null);
        } catch (Exception e) {
            log.error("Error while logging out user: {}", e.getMessage());
            return responseUtil.internalServerErrorResponse("Exception while logging out user: ");
        }
    }
}
