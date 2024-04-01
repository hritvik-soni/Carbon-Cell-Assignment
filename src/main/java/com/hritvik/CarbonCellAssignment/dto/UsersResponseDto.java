package com.hritvik.CarbonCellAssignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersResponseDto {

    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private LocalDateTime createdAtTs;
    private LocalDateTime updatedAtTs;
}
