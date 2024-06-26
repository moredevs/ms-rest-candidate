package com.microservice.rest.candidate.mappers;


import com.microservice.rest.candidate.dto.CandidateDTO;
import com.microservice.rest.candidate.dto.request.CandidateRequest;
import com.microservice.rest.candidate.domain.Candidate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CandidateMapper {
    CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

    Candidate toDomain(CandidateRequest candidateRequest);

    CandidateDTO toCandidateDTO(Candidate candidate);
    @Mapping(target = "id", ignore = true) // Ignora la asignación del ID para evitar cambios accidentales
    void updateCandidateFromRequest(CandidateRequest candidateRequest, @MappingTarget Candidate candidate);

}
