package com.seguros.dao;

import com.seguros.model.Cliente;

public interface ClienteDaoInterface {


    //Métodos

    boolean cadastrarCliente(Cliente cliente);
    boolean verificarLogin(String email, String senha);
    boolean atualizarCliente(Cliente cliente);
    Cliente buscarPorCpf(String cpf);
    boolean removerClientePorCpf(String cpf);
}
