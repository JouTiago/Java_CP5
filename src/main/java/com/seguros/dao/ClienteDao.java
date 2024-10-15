package com.seguros.dao;

import com.seguros.model.Cliente;
import com.seguros.util.OperacoesSql;

public class ClienteDao {
    private static final String TABELA = "T_CLIENTE";
    private static final String[] COLUNAS = {"c_cpf", "c_email", "c_senha"};


    //Métodos

    //Cadastrar novo cliente
    public boolean cadastrarCliente(Cliente cliente) {
        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {cliente.getCpf(), cliente.getEmail(), cliente.getSenha()};
        return OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

    //Verificar login
    public boolean verificarLogin(String email, String senha) {
        return OperacoesSql.verificaLogin(TABELA, email, senha);
    }

    //Atualizar os dados do cliente no banco
    public boolean atualizarCliente(Cliente cliente) {
        String[] colunasAtualizar = {"c_email", "c_senha"};
        Object[] valoresAtualizar = {cliente.getEmail(), cliente.getSenha()};

        String condicao = "c_cpf = ?";
        Object[] parametrosCondicao = {cliente.getCpf()};

        return OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }
}
