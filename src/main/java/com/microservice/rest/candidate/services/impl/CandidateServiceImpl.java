package com.microservice.rest.candidate.services.impl;


import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.CandidateDTO;
import com.microservice.rest.candidate.dto.request.CandidateRequest;
import com.microservice.rest.candidate.domain.Candidate;
import com.microservice.rest.candidate.mappers.CandidateMapper;
import com.microservice.rest.candidate.repository.CandidateRepository;
import com.microservice.rest.candidate.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public ApiResponseGeneric<CandidateDTO> saveCandidate(CandidateRequest candidateRequest) {
        return Mono.justOrEmpty(candidateRequest)
                .map(CandidateMapper.INSTANCE::toDomain)
                .flatMap(candidate ->
                        Mono.just(candidateRepository.save(candidate))
                                .flatMap(savedCandidate -> Mono.just(CandidateMapper.INSTANCE.toCandidateDTO(savedCandidate)))
                                .onErrorResume(e -> Mono.error(new RuntimeException("Error al convertir el candidato a DTO", e)))
                )
                .map(candidateDTO -> new ApiResponseGeneric<>(1, "Registro exitoso", candidateDTO))
                .onErrorResume(e -> Mono.just(new ApiResponseGeneric<>(0, e.getMessage(), null))).block();
    }

    @Override
    public ApiResponseGeneric<CandidateDTO> updateCandidate(Long id, CandidateRequest candidateRequest) {
        return Mono.fromCallable(() -> {
            Optional<Candidate> existingCandidateOpt = candidateRepository.findById(id);
            if (existingCandidateOpt.isEmpty()) {
                return new ApiResponseGeneric<>(HttpStatus.NOT_FOUND.value(), "Candidate not found", new CandidateDTO());
            }

            Candidate existingCandidate = existingCandidateOpt.get();
            CandidateMapper.INSTANCE.updateCandidateFromRequest(candidateRequest, existingCandidate);
            Candidate updatedCandidate = candidateRepository.save(existingCandidate);

            CandidateDTO candidateDTO = CandidateMapper.INSTANCE.toCandidateDTO(updatedCandidate);
            return new ApiResponseGeneric<>(HttpStatus.OK.value(), "Candidate updated successfully", candidateDTO);
        }).block();
    }

    @Override
    public ApiResponseGeneric<CandidateDTO> getCandidateById(Long id) {
        return Mono.justOrEmpty(candidateRepository.findById(id))
                .flatMap(candidate -> {
                    try {
                        CandidateDTO candidateDTO = CandidateMapper.INSTANCE.toCandidateDTO(candidate);
                        return Mono.just(new ApiResponseGeneric<>(1, "Consulta exitosa", candidateDTO));
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Error al convertir el candidato a DTO", e));
                    }
                })
                .switchIfEmpty(Mono.just(new ApiResponseGeneric<>(0, "No se encontró candidato con el ID: " + id, null)))
                .onErrorResume(e -> Mono.just(new ApiResponseGeneric<>(0, e.getMessage(), null))).block();
    }

    @Override
    public ApiResponseGeneric<List<CandidateDTO>> getAllCandidates() {
        return Flux.fromIterable(candidateRepository.findAll())
                .map(CandidateMapper.INSTANCE::toCandidateDTO)
                .collectList()
                .map(candidateDTOList -> new ApiResponseGeneric<>(1, "Consulta exitosa", candidateDTOList))
                .onErrorResume(e -> Mono.just(new ApiResponseGeneric<>(0, e.getMessage(), null)))
                .block();
    }


    @Override
    public ApiResponseGeneric<String> deleteCandidateById(Long id) {
        return Mono.fromRunnable(() -> candidateRepository.deleteById(id))
                .then(Mono.just(new ApiResponseGeneric<>(1, "Eliminación exitosa","OK")))
                .onErrorResume(e -> Mono.just(new ApiResponseGeneric<>(0, "No se encontró candidato con el ID: " + id,"NOT_FOUND")))
                .block();
    }
}
