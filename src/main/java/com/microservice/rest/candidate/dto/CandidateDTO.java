package com.microservice.rest.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private Long id;
    private String name;

    private String email;

    private String gender;

    private BigDecimal expectedSalary;

    private String additionalInfo;
}
