package com.devpedrogo.tre_testes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devpedrogo.tre_testes.model.UsuarioEntity;
import com.devpedrogo.tre_testes.repository.IUsuarioRepository;

@Service
public class ConsultaService {

    private final IUsuarioRepository usuarioRepository;

    public ConsultaService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioEntity> consultarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioEntity> consultarUsuarioPorNomeEmailECpf(String nome, String email, String cpf) {
        // 1. Regra de negócio: Se QUALQUER um for nulo ou em branco, interrompe e retorna lista vazia
        if (isVazio(nome) || isVazio(email) || isVazio(cpf)) {
            return List.of(); // Retorna [] sem ir ao banco
        }

        // 2. Normalização dos dados
        String nomeFiltro = nome.trim();
        String emailFiltro = email.trim();
        String cpfFiltro = cpf.replaceAll("\\D", "");

        // 3. Executa a busca no banco apenas se todos os dados foram preenchidos
        return usuarioRepository.consultarUsuarioPorNomeEmailECpf(nomeFiltro, emailFiltro, cpfFiltro);
    }

    private boolean isVazio(String texto) {
        return texto == null || texto.isBlank();
    }

}
