package com.microservice.rest.candidate.services;


import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.LoginRequestDto;
import com.microservice.rest.candidate.dto.LoginResponseDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<ApiResponseGeneric<LoginResponseDto>> authenticate(LoginRequestDto loginRequest);

}
