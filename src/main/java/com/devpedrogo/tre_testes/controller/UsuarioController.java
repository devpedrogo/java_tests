package com.devpedrogo.tre_testes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devpedrogo.tre_testes.dto.UsuarioResponseDto;
import com.devpedrogo.tre_testes.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "API para consulta de usuários")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(
        summary = "Consulta todos os usuários", 
        description = "Retorna uma lista de todos os usuários cadastrados"
    )  
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDto> consultarUsuarios() {
        return usuarioService.consultarUsuarios();
    }

    @GetMapping("/filtro")
    @Operation(
        summary = "Consulta usuários por nome, email ou CPF", 
        description = "Retorna uma lista de usuários que correspondem aos critérios de pesquisa fornecidos"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailOuCpf(@RequestParam(required = false) String nome,
                                                                 @RequestParam(required = false) String email,
                                                                 @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 números")
                                                                 @RequestParam(required = false) String cpf) {
        return usuarioService.consultarUsuarioPorNomeEmailOuCpf(nome, email, cpf);
    }

    @GetMapping("/{nome}/{email}/{cpf}")
    @Operation(
        summary = "Consulta usuários por nome, email e CPF", 
        description = "Retorna uma lista de usuários que correspondem exatamente aos critérios de pesquisa fornecidos"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailECpf(@PathVariable String nome, @PathVariable String email, @PathVariable String cpf){
        return usuarioService.consultarUsuarioPorNomeEmailECpf(nome, email, cpf);
    }
}
