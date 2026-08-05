package com.devpedrogo.tre_testes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios", indexes = {
    @Index(name = "idx_usuarios_cpf", columnList = "cpf"),
    @Index(name = "idx_usuarios_nome_email", columnList = "nome, email")
})
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;
}
