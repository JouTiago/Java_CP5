package com.seguros.dao;

import com.seguros.model.Cliente;
import com.seguros.util.OperacoesSql;

public class ClienteDao implements ClienteDaoInterface {
    private static final String TABELA = "T_CLIENTE";
    private static final String[] COLUNAS = {"c_cpf", "c_email", "c_senha"};


    //Métodos

    //Cadastrar novo cliente
    @Override
    public boolean cadastrarCliente(Cliente cliente) {

        if (OperacoesSql.clienteExiste(TABELA, cliente.getCpf())) {
            throw new IllegalArgumentException("Cliente já cadastrado com esse CPF.");
        }

        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {cliente.getCpf(), cliente.getEmail(), cliente.getSenha()};
        return OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

    //Verificar login
    @Override
    public boolean verificarLogin(String email, String senha) {
        return OperacoesSql.verificaLogin(TABELA, email, senha);
    }

    //Atualizar os dados do cliente no banco
    @Override
    public boolean atualizarCliente(Cliente cliente) {
        String[] colunasAtualizar = {"c_email", "c_senha"};
        Object[] valoresAtualizar = {cliente.getEmail(), cliente.getSenha()};

        String condicao = "c_cpf = ?";
        Object[] parametrosCondicao = {cliente.getCpf()};

        return OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }


    //Buscar um cliente pelo CPF
    @Override
    public Cliente buscarPorCpf(String cpf) {
        return OperacoesSql.buscarClientePorCpf(cpf);
    }


    //Remover um cliente pelo CPF
    @Override
    public boolean removerClientePorCpf(String cpf) {
        String condicao = "c_cpf = ?";
        Object[] parametrosCondicao = {cpf};

        return OperacoesSql.executarDelete(TABELA, condicao, parametrosCondicao);
    }
}
