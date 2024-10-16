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

    //Validação de senha (senha deve ter no mínimo 8 caracteres, incluindo letras e números)
    public boolean validarSenha() {
        return this.senha != null && this.senha.length() >= 8 && this.senha.matches(".*\\d.*")
                && this.senha.matches(".*[a-zA-Z]*.");
    }

    //Validação de email
    public boolean validarEmail() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return this.email != null && this.email.matches(emailRegex);
    }

    //Alterar senha (para alterar a senha deve confirmar a senha atual)
    public boolean alterarSenha(String senhaAtual, String novaSenha) {
        if (this.senha.equals(senhaAtual) && novaSenha.length() >= 8 && novaSenha.matches(".*\\d.*")
        && novaSenha.matches(".*[a-zA-Z]*.*")) {
            this.senha = novaSenha;
            return true;
        }
        return false;
    }

    //Alterar email após validar
    public boolean alterarEmail(String novoEmail) {
        if (novoEmail != null && novoEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.email = novoEmail;
            return true;
        }
        return false;
    }


}
