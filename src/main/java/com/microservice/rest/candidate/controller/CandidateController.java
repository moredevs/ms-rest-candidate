package com.microservice.rest.candidate.controller;


import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.CandidateDTO;
import com.microservice.rest.candidate.dto.request.CandidateRequest;
import com.microservice.rest.candidate.services.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/candidates")
@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Registrar candidato", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ApiResponseGeneric<CandidateDTO>> createCandidate(@RequestBody CandidateRequest candidate) {
        return new ResponseEntity<>(candidateService.saveCandidate(candidate), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<CandidateDTO>> updateCandidate(
            @PathVariable Long id, @RequestBody CandidateRequest candidateRequest) {
        return new ResponseEntity<>(candidateService.updateCandidate(id, candidateRequest), HttpStatus.OK);
    }

    @Operation(summary = "Obtener Candidatos por Id candidato", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<CandidateDTO>> getCandidateById(@PathVariable Long id) {
        return new ResponseEntity<>(candidateService.getCandidateById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponseGeneric<List<CandidateDTO>>> getAllCandidates() {
        return new ResponseEntity<>(candidateService.getAllCandidates(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseGeneric<String>> deleteCandidateById(@PathVariable Long id) {
        return new ResponseEntity<>(candidateService.deleteCandidateById(id), HttpStatus.OK);
     }
}