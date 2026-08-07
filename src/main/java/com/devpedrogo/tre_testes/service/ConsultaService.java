package com.devpedrogo.tre_testes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devpedrogo.tre_testes.dto.UsuarioResponseDto;
import com.devpedrogo.tre_testes.model.UsuarioEntity;
import com.devpedrogo.tre_testes.repository.IUsuarioRepository;
import com.devpedrogo.tre_testes.repository.UsuarioDao;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaService {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioDao usuarioDao;

    public ConsultaService(IUsuarioRepository usuarioRepository, UsuarioDao usuarioDao) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioDao = usuarioDao;
    }

    public List<UsuarioResponseDto> consultarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioResponseDto(usuario.getNome(), usuario.getEmail(), usuario.getCpf()))
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailECpf(String nome, String email, String cpf) {
        // 1. Regra de negócio: Se QUALQUER um for nulo ou em branco, interrompe e retorna lista vazia
        if (isVazio(nome) || isVazio(email) || isVazio(cpf)) {
            return List.of(); // Retorna [] sem ir ao banco
        }

        // 2. Normalização dos dados
        String nomeFiltro = nome.trim();
        String emailFiltro = email.trim();
        String cpfFiltro = cpf.replaceAll("\\D", "");

        // 3. Executa a busca no banco apenas se todos os dados foram preenchidos
        return usuarioRepository.consultarUsuarioPorNomeEmailECpf(nomeFiltro, emailFiltro, cpfFiltro).stream()
                .map(usuario -> new UsuarioResponseDto(usuario.getNome(), usuario.getEmail(), usuario.getCpf()))
                .collect(Collectors.toList());
    }

    private boolean isVazio(String texto) {
        return texto == null || texto.isBlank();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> consultarUsuarioPorNomeEmailOuCpf(String nome, String email, String cpf) {
        // Guarda: Se NENHUM filtro foi informado, nem vai ao banco de dados
        if (isVazio(nome) && isVazio(email) && isVazio(cpf)) {
            return List.of();
        }

        // Sanitiza os dados antes de passar para a query nativa
        String nomeFiltro = isVazio(nome) ? null : nome.trim();
        String emailFiltro = isVazio(email) ? null : email.trim();
        String cpfFiltro = isVazio(cpf) ? null : cpf.replaceAll("\\D", "");

        List<UsuarioEntity> entidades = usuarioDao.consultarUsuarioPorNomeEmailOuCpf(
                nomeFiltro, 
                emailFiltro, 
                cpfFiltro
        );

        return entidades.stream()
                .map(usuario -> new UsuarioResponseDto(usuario.getNome(), usuario.getEmail(), usuario.getCpf()))
                .toList();
    }

}
