package com.devpedrogo.tre_testes.repository;

import com.devpedrogo.tre_testes.model.UsuarioEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDao {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UsuarioEntity> consultarUsuarioPorNomeEmailOuCpf(String nome, String email, String cpf) {
        StringBuilder sql = new StringBuilder("SELECT nome, email, cpf FROM usuarios WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sql.append(" AND LOWER(nome) LIKE LOWER(?) ");
            parametros.add("%" + nome.trim() + "%");
        }

        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND LOWER(email) LIKE LOWER(?) ");
            parametros.add(email.trim());
        }

        if (cpf != null && !cpf.trim().isEmpty()) {
            sql.append(" AND REPLACE(REPLACE(cpf, '.', ''), '-', '') = ? ");
            parametros.add(cpf.replaceAll("\\D", ""));
        }

        if (parametros.isEmpty()) {
            return List.of();
        }

        return jdbcTemplate.query(
            sql.toString(),
            (rs, rowNum) -> new UsuarioEntity(
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cpf")
            ), parametros.toArray()
        );
    }
}