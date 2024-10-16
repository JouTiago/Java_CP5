package com.seguros.factory;

import com.seguros.model.Cliente;

public class ClienteFactory implements EntityFactory<Cliente> {
    private String cpf;
    private String email;
    private String senha;


    //Construtores
    public ClienteFactory(String cpf, String email, String senha) {
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }


    //Métodos
    @Override
    public Cliente criar() {
        return new Cliente(cpf, email, senha);
    }
}
