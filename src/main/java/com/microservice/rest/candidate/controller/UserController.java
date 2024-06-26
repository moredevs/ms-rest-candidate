package com.microservice.rest.candidate.controller;

import com.microservice.rest.candidate.dto.ApiResponseGeneric;
import com.microservice.rest.candidate.dto.LoginRequestDto;
import com.microservice.rest.candidate.dto.LoginResponseDto;
import com.microservice.rest.candidate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/usuarios")
@RestController
public class UserController {
    @Autowired
    private UserService usuarioService;


    @PostMapping("/login")
    public Mono<ApiResponseGeneric<LoginResponseDto>> loginUsuario(@RequestBody LoginRequestDto registroUsuarioDto) {
        return usuarioService.authenticate(registroUsuarioDto);
    }

}
