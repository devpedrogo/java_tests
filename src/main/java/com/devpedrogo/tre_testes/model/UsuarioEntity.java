package com.devpedrogo.tre_testes.model;

public class UsuarioEntity {
    private String nome;
    private String email;
    private String cpf;

    // Constructors
    public UsuarioEntity() {
    }

    public UsuarioEntity(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
