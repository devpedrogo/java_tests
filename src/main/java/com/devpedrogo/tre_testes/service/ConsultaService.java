package com.devpedrogo.tre_testes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devpedrogo.tre_testes.model.UsuarioEntity;

@Service
public class ConsultaService {

    List<UsuarioEntity> usuariosMocados = List.of(
        new UsuarioEntity("Carlos Silva", "carlos.silva@tre.jus.br", "12345678901"),
        new UsuarioEntity("Ana Costa", "ana.costa@tre.jus.br", "98765432100"),
        new UsuarioEntity("Bruno Souza", "bruno.souza@tre.jus.br", "45678912300"),
        new UsuarioEntity("Pedro Gouveia", "pedro@tre.jus.br", "11054073435")
    );

    public List<UsuarioEntity> consultarUsuarios() {
        return usuariosMocados;
    }

    public List<UsuarioEntity> consultarUsuarioPorNomeEmailOuCpf(String nome, String email, String cpf) {
        // Se o cliente não passou nenhum filtro, retorna a lista vazia para poupar processamento
        if (nome == null && email == null && cpf == null) {
            return List.of(); 
        }

        return usuariosMocados.stream()
                .filter(usuario -> {
                    // Se o parâmetro foi enviado, o campo do usuário deve conter o termo buscado (ignorando maiúsculas/minúsculas)
                    boolean matchesNome = (nome != null) && usuario.getNome().toLowerCase().contains(nome.toLowerCase());
                    boolean matchesEmail = (email != null) && usuario.getEmail().toLowerCase().contains(email.toLowerCase());
                    boolean matchesCpf = (cpf != null) && usuario.getCpf().equals(cpf);

                    // Retorna verdadeiro se o usuário bateu com QUALQUER um dos filtros enviados
                    return matchesNome || matchesEmail || matchesCpf;
                })
                .toList();
    }

    public List<UsuarioEntity> consultarUsuarioPorNomeEmailECpf(String nome, String email, String cpf) {
        // 1. Validação estrita: Se QUALQUER um for nulo ou vazio, barra a consulta na hora
        if (nome == null || nome.isBlank() || 
            email == null || email.isBlank() || 
            cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("Todos os campos (nome, email e CPF) são obrigatórios.");
        }

        // 2. Normalização (Feita uma única vez para performance)
        String nomeFiltro = nome.trim().toLowerCase();
        String emailFiltro = email.trim().toLowerCase();
        String cpfFiltro = cpf.trim();

        // 3. Filtro com correspondência total (E)
        return usuariosMocados.stream()
                .filter(usuario -> usuario != null 
                        && usuario.getNome() != null 
                        && usuario.getEmail() != null 
                        && usuario.getCpf() != null)
                .filter(usuario -> usuario.getNome().toLowerCase().contains(nomeFiltro)
                        && usuario.getEmail().toLowerCase().contains(emailFiltro)
                        && usuario.getCpf().equals(cpfFiltro))
                .toList();
    }

}
