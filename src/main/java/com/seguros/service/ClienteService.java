package com.seguros.service;

import com.seguros.dao.ClienteDao;
import com.seguros.factory.ClienteFactory;
import com.seguros.model.Cliente;

public class ClienteService {
    private final ClienteDao clienteDao = new ClienteDao();

    //Métodos

    //Cadastrar um cliente com validações
    public boolean cadastrarCliente(String cpf, String email, String senha) {
        ClienteFactory clienteFactory = new ClienteFactory(cpf, email, senha);
        Cliente cliente = clienteFactory.criar();

        //Validar a senha
        if (!cliente.validarSenha()) {
            throw new IllegalArgumentException("A senha deve conter pelo menos 8 caracteres.");
        }

        //Validar email
        if (!cliente.validarEmail()) {
            throw new IllegalArgumentException("Email inválido.");
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


    //Buscar o cliente pelo CPF (chave primária)
    public Cliente buscarClientePorCpf(String cpf) {
        return clienteDao.buscarPorCpf(cpf);
    }


    //Alterar a senha
    public boolean alterarSenha(Cliente cliente, String senhaAtual, String novaSenha) {
        if (cliente.alterarSenha(senhaAtual, novaSenha)) {
            return clienteDao.atualizarCliente(cliente);
        }
        throw new IllegalArgumentException("Senha atual incorreta ou nova senha inválida.");
    }

    //Alterar o email
    public boolean alterarEmail(Cliente cliente, String novoEmail) {
        if (cliente.alterarEmail(novoEmail)) {
            return clienteDao.atualizarCliente(cliente);
        }
        throw new IllegalArgumentException("Email inválido.");
    }

}
