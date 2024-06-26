package com.microservice.rest.candidate.services.impl;

import com.microservice.rest.candidate.auth.JwtConfig;
import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.LoginRequestDto;
import com.microservice.rest.candidate.dto.LoginResponseDto;
import com.microservice.rest.candidate.repository.UserRepository;
import com.microservice.rest.candidate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
     private UserRepository userRepository;


    @Autowired
     private ReactiveAuthenticationManager  authenticationManager;

    @Autowired
     private JwtConfig jwtConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public Mono<ApiResponseGeneric<LoginResponseDto>> authenticate(LoginRequestDto loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());
        return this.authenticationManager.authenticate(authToken)
                .flatMap(authentication -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return Mono.just(userRepository.findByUsername(loginRequest.getUsername()))
                            .flatMap(usuario -> {
                                String jwt = jwtConfig.generateToken(usuario);
                                LoginResponseDto responseDto = new LoginResponseDto();
                                responseDto.setToken(jwt);
                                return Mono.just(new ApiResponseGeneric<>(1, "Autenticación exitosa", responseDto));
                            });
                })
                .onErrorResume(AuthenticationException.class, e ->
                        Mono.just(new ApiResponseGeneric<>(0, "Credenciales inválidas", new LoginResponseDto()))
                )
                .onErrorResume(e -> Mono.just(new ApiResponseGeneric<>(0, e.getMessage(), null)));
    }



}
