package com.hritvik.CarbonCellAssignment.controller;

import com.hritvik.CarbonCellAssignment.service.DataRetrievalService;
import com.hritvik.CarbonCellAssignment.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data-retrieval")
public class DataRetrievalController {

    @Autowired
    DataRetrievalService dataRetrievalService;

    @Operation(
            method = "GET",
            summary = "Get data by category",
            description = "Retrieve data based on category and limit",
            parameters = {
                    @Parameter(name = "category", in = ParameterIn.QUERY, required = false, description = "Category of data to be retrieved"),
                    @Parameter(name = "limit", in = ParameterIn.QUERY, required = false, description = "Limit of data to be retrieved")
            },
            security = @SecurityRequirement(name = "BearerToken"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Data retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))}
    )
    @GetMapping("/category")
    public Response getDataByCategory(@RequestParam(required = false, defaultValue = "NA") String category,
                                      @RequestParam(required = false, defaultValue = "100") int limit) {
        return dataRetrievalService.getDataByCategory(category, limit);
    }


}
