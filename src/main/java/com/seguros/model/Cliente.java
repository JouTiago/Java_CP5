package com.seguros.model;

public class Cliente {
    private String cpf;
    private String email;
    private String senha;

    //Construtores
    public Cliente(String cpf, String email, String senha) {
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }


    // Getters e Setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }


    //Métodos

    //Validação de senha (senha deve ter no mínimo 8 caracteres)
    public boolean validarSenha() {
        return this.senha != null && this.senha.length() >= 8;
    }
}
