package com.microservice.rest.candidate.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CandidateRequest {
    private String name;

     private String email;

     private String gender;

     private BigDecimal expectedSalary;

     private String additionalInfo;
}
