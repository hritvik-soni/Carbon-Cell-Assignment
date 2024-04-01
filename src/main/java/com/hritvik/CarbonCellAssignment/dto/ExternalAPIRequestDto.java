package com.hritvik.CarbonCellAssignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalAPIRequestDto {


    private int count;
    private List<Entities> entries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Entities {

        @JsonProperty("API")
        private String API;

        @JsonProperty("Description")
        private String Description;

        @JsonProperty("Auth")
        private String Auth;

        @JsonProperty("HTTPS")
        private boolean HTTPS;

        @JsonProperty("Cors")
        private String Cors;

        @JsonProperty("Link")
        private String Link;

        @JsonProperty("Category")
        private String Category;

    }
}


