package com.hritvik.CarbonCellAssignment.controller;


import com.hritvik.CarbonCellAssignment.dto.UserLoginRequestDto;
import com.hritvik.CarbonCellAssignment.service.AuthService;
import com.hritvik.CarbonCellAssignment.utils.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(
            method = "POST",
            summary = "Login user",
            description = "Authenticate user and generate JWT token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = UserLoginRequestDto.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(schema = @Schema(implementation = Response.class)))
            }
    )
    @PostMapping("/login")
    public Response loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        return authService.loginUser(userLoginRequestDto);
    }

    @Operation(
            method = "DELETE",
            summary = "Logout user",
            description = "Invalidate JWT token and logout user",
            parameters = {
                    @Parameter(name = "Authorization", required = true, description = "JWT token of the user")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged out successfully", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            security = {
                    @SecurityRequirement(name = "BearerToken")
            }
    )
    @DeleteMapping("/logout")
    public Response logoutUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return authService.logoutUser(token);
    }

}
