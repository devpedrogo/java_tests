package com.devpedrogo.tre_testes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devpedrogo.tre_testes.dto.UsuarioResponseDto;
import com.devpedrogo.tre_testes.service.ConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "API para consulta de usuários")
public class UsuarioController {

    private final ConsultaService consultaService;

    public UsuarioController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    @Operation(
        summary = "Consulta todos os usuários", 
        description = "Retorna uma lista de todos os usuários cadastrados"
    )  
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDto> consultarUsuarios() {
        return consultaService.consultarUsuarios();
    }

    @GetMapping("/filtro")
    @Operation(
        summary = "Consulta usuários por nome, email ou CPF", 
        description = "Retorna uma lista de usuários que correspondem aos critérios de pesquisa fornecidos"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailOuCpf(@RequestParam(required = false) String nome,
                                                                 @RequestParam(required = false) String email,
                                                                 @RequestParam(required = false) String cpf) {
        return consultaService.consultarUsuarioPorNomeEmailOuCpf(nome, email, cpf);
    }

    @GetMapping("/{nome}/{email}/{cpf}")
    @Operation(
        summary = "Consulta usuários por nome, email e CPF", 
        description = "Retorna uma lista de usuários que correspondem exatamente aos critérios de pesquisa fornecidos"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailECpf(@PathVariable String nome, @PathVariable String email, @PathVariable String cpf){
        return consultaService.consultarUsuarioPorNomeEmailECpf(nome, email, cpf);
    }
}
