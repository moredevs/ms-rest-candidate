package com.microservice.rest.candidate.services;


import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.CandidateDTO;
import com.microservice.rest.candidate.dto.request.CandidateRequest;

import java.util.List;

public interface CandidateService {
    ApiResponseGeneric<CandidateDTO> saveCandidate(CandidateRequest candidate);
    ApiResponseGeneric<CandidateDTO> updateCandidate(Long id, CandidateRequest candidate);
    ApiResponseGeneric<CandidateDTO> getCandidateById(Long id);
    ApiResponseGeneric<List<CandidateDTO>> getAllCandidates();
    ApiResponseGeneric<String> deleteCandidateById(Long id);
}
