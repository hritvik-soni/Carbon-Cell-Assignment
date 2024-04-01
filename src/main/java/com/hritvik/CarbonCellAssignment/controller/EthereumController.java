package com.hritvik.CarbonCellAssignment.controller;

import com.hritvik.CarbonCellAssignment.service.EthereumService;
import com.hritvik.CarbonCellAssignment.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/ethereum")
public class EthereumController {

    @Autowired
    EthereumService ethereumService;

    @Operation(
            method = "GET",
            summary = "Get Ethereum account balance",
            description = "Retrieve the balance of a specified Ethereum account",
            parameters = {
                    @Parameter(name = "accountAddress", description = "Ethereum account address", required = true)
            },
            security = @SecurityRequirement(name = "BearerToken"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = Response.class)))
            }
    )
    @GetMapping("/balance")
    public Response getAccountBalance(@RequestParam String accountAddress) {
        return ethereumService.getAccountBalance(accountAddress);
    }
}
