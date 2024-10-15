package com.seguros.service;

import com.seguros.dao.ClienteDao;
import com.seguros.model.Cliente;

public class ClienteService {
    private final ClienteDao clienteDao = new ClienteDao();

    //Métodos

    //Cadastrar um cliente com validações
    public boolean cadastrarCliente(Cliente cliente) {

        //Validar a senha
        if (!cliente.validarSenha()) {
            throw new IllegalArgumentException("A senha deve conter pelo menos 8 caracteres.");
        }

        //Regra de negócio: Verificar se o cliente já está cadastrado pelo CPF
        if (clienteDao.verificarLogin(cliente.getEmail(), cliente.getSenha())) {
            throw new IllegalArgumentException("Cliente já cadastrado com esse CPF.");
        }

        //Realizar o cadastro
        return clienteDao.cadastrarCliente(cliente);
    }


    //Realizar login
    public boolean realizarLogin(String email, String senha) {
        return clienteDao.verificarLogin(email, senha);
    }
}
