package com.microservice.rest.candidate.services.impl;

import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.CandidateDTO;
import com.microservice.rest.candidate.dto.request.CandidateRequest;
import com.microservice.rest.candidate.domain.Candidate;
import com.microservice.rest.candidate.mappers.CandidateMapper;
import com.microservice.rest.candidate.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private CandidateMapper candidateMapper;
    @Test
    void saveCandidate_Success() {
        CandidateRequest candidateRequest = new CandidateRequest("John Doe", "johndoe@example.com", "Male", new BigDecimal(100000), "Some additional information");
        Candidate mockCandidate = new Candidate();

        Mockito.when(candidateRepository.save(any(Candidate.class))).thenReturn(mockCandidate); // Single stubbing

        ApiResponseGeneric<CandidateDTO> response = candidateService.saveCandidate(candidateRequest);

        assertNotNull(response);
        assertEquals(1, response.getCode());
        assertEquals("Registro exitoso", response.getMessage());
    }

    @Test
    public void testSaveCandidate_Error() {
        CandidateRequest candidateRequest = new CandidateRequest("Jane Doe", "janedoe@example.com", "Female", new BigDecimal(80000), "");

        Mockito.when(candidateRepository.save(any(Candidate.class))).thenThrow(new RuntimeException("Error saving candidate"));

        ApiResponseGeneric<CandidateDTO> response = candidateService.saveCandidate(candidateRequest);

        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertTrue(response.getMessage().contains("Error saving candidate"));
        assertNull(response.getData());
    }

    @Test
    public void testUpdateCandidate_Success() {
        Long candidateId = 1L;
        CandidateRequest updateRequest = new CandidateRequest("John Doe (Updated)", "johndoe.updated@example.com", "Male", new BigDecimal(120000), "Some additional information (updated)");
        Candidate existingCandidate = new Candidate();

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(existingCandidate));
        Mockito.when(candidateRepository.save(existingCandidate)).thenReturn(existingCandidate);

        ApiResponseGeneric<CandidateDTO> response = candidateService.updateCandidate(candidateId, updateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("Candidate updated successfully", response.getMessage());
    }

    @Test
    public void testUpdateCandidate_NotFound() {
        Long candidateId = 1L;
        CandidateRequest updateRequest = new CandidateRequest("John Doe (Updated)", "johndoe.updated@example.com", "Male", new BigDecimal(120000), "Some additional information (updated)");

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        ApiResponseGeneric<CandidateDTO> response = candidateService.updateCandidate(candidateId, updateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getCode()); // Expect Not Found (404)
        assertEquals("Candidate not found", response.getMessage());
    }

    @Test
    public void testGetCandidateById_Success() {
        Long candidateId = 1L;
        Candidate candidate = new Candidate();

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        ApiResponseGeneric<CandidateDTO> response = candidateService.getCandidateById(candidateId);

        assertNotNull(response);
        assertEquals(1, response.getCode());
        assertEquals("Consulta exitosa", response.getMessage());
    }

    @Test
    public void testGetCandidateById_NotFound() {
        Long candidateId = 1L;

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        ApiResponseGeneric<CandidateDTO> response = candidateService.getCandidateById(candidateId);

        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("No se encontró candidato con el ID: " + candidateId, response.getMessage());
    }
}