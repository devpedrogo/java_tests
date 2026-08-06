package com.devpedrogo.tre_testes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devpedrogo.tre_testes.dto.UsuarioResponseDto;
import com.devpedrogo.tre_testes.service.ConsultaService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final ConsultaService consultaService;

    public UsuarioController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public List<UsuarioResponseDto> consultarUsuarios() {
        return consultaService.consultarUsuarios();
    }

    @GetMapping("/filtro")
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailOuCpf(@RequestParam(required = false) String nome,
                                                                 @RequestParam(required = false) String email,
                                                                 @RequestParam(required = false) String cpf) {
        return consultaService.consultarUsuarioPorNomeEmailOuCpf(nome, email, cpf);
    }

    @GetMapping("/{nome}/{email}/{cpf}")
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailECpf(@PathVariable String nome, @PathVariable String email, @PathVariable String cpf){
        return consultaService.consultarUsuarioPorNomeEmailECpf(nome, email, cpf);
    }
}
