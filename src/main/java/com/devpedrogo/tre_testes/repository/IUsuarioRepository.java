package com.devpedrogo.tre_testes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devpedrogo.tre_testes.model.UsuarioEntity;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    @Query(value = """
        SELECT * FROM usuarios u
        WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
          AND LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))
          AND REGEXP_REPLACE(u.cpf, '\\D', '', 'g') = :cpf
        """, nativeQuery = true)
    List<UsuarioEntity> consultarUsuarioPorNomeEmailECpf(
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("cpf") String cpf);

    @Query(value = """
        SELECT * FROM usuarios u
        WHERE (:nome IS NOT NULL AND :nome <> '' AND LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
           OR (:email IS NOT NULL AND :email <> '' AND LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
           OR (:cpf IS NOT NULL AND :cpf <> '' AND REGEXP_REPLACE(u.cpf, '\\D', '', 'g') = :cpf)
        """, nativeQuery = true)
    List<UsuarioEntity> consultarUsuarioPorNomeEmailOuCpf(
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("cpf") String cpf);
}
