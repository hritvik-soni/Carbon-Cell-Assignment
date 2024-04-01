package com.hritvik.CarbonCellAssignment.controller;

import com.hritvik.CarbonCellAssignment.dto.UserRequestDto;
import com.hritvik.CarbonCellAssignment.service.UsersService;
import com.hritvik.CarbonCellAssignment.utils.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @Operation(
            method = "POST",
            summary = "Register a new user",
            description = "Create a new user with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User register successfully", content = @Content(schema = @Schema(implementation = Response.class)))
            }

    )
    @PostMapping("/register")
    public Response createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return usersService.createUser(userRequestDto);
    }

    @Operation(
            method = "DELETE",
            summary = "Delete a user by ID",
            description = "Delete the user with the specified ID",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "BearerToken"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User register successfully", content = @Content(schema = @Schema(implementation = Response.class)))
            })
    @DeleteMapping("/{id}")
    public Response deleteUser(@AuthenticationPrincipal String username, @PathVariable String id) {
        return usersService.deleteUser(id, username);
    }

    @Operation(
            method = "GET",
            summary = "Get a user by ID",
            description = "Get the user with the specified ID",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "BearerToken"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
            })
    @GetMapping("/{id}")
    public Response getUserById(@PathVariable String id) {
        return usersService.getUserById(id);
    }

    @Operation(
            method = "GET",
            summary = "Get all users",
            description = "Get details of all registered users",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "BearerToken"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
            })
    @GetMapping("/all")
    public Response getAllUsers() {
        return usersService.getAllUsers();
    }

}
