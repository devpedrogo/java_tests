package com.devpedrogo.tre_testes.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.devpedrogo.tre_testes.model.SetorEntity;

@Repository
public class SetorDao {

    private final JdbcTemplate jdbcTemplate;

    public SetorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SetorEntity> listarSetores() {
        String sql = "SELECT codigo_setor, nome_setor, sigla_setor FROM setores";
        
        return jdbcTemplate.query(sql, (rs, rowNum) -> new SetorEntity(
                rs.getBigDecimal("codigo_setor"),
                rs.getString("nome_setor"),
                rs.getString("sigla_setor")
        ));
    }
}
