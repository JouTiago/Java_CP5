package com.seguros.util;

import com.seguros.config.DatabaseConfig;
import com.seguros.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperacoesSql {


    //Gerar um novo ID baseado no valor máximo da coluna ID
    public static int gerarNovoId(String nomeTabela, String nomeColunaId) {
        int novoId = 0;
        String sql = "SELECT MAX(" + nomeColunaId + ") AS MAX_ID FROM " + nomeTabela;

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                novoId = resultSet.getInt("MAX_ID");
                if (resultSet.wasNull()) {
                    novoId = 1;
                } else {
                    novoId++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao gerar novo ID: " + e.getMessage());
        }
        return novoId;
    }


    //Gerar o SQL de insert com base nas colunas recebidas
    public static String gerarInsertTabela(String nomeTabela, String[] colunas) {
        StringBuilder colunasTabela = new StringBuilder();
        StringBuilder valoresTabela = new StringBuilder();

        for (int i = 0; i < colunas.length; i++) {
            colunasTabela.append(colunas[i]);
            valoresTabela.append("?");

            if (i < colunas.length - 1) {
                colunasTabela.append(", ");
                valoresTabela.append(", ");
            }
        }
        return "INSERT INTO "+nomeTabela+" ("+colunasTabela.toString()+") VALUES ("+valoresTabela.toString()+")";
    }


    //Realizar a inserção no banco de dados
    public static boolean inserirNoBanco(String tabela, Object[] valores, String sqlInsert) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {

            for (int i = 0; i < valores.length; i++) {
                preparedStatement.setObject(i + 1, valores[i]);
            }

            int colunasAfetadas = preparedStatement.executeUpdate();
            return colunasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir na tabela " + tabela + ": " + e.getMessage());
            return false;
        }
    }


    //Verificar se login é válido no banco de dados
    public static boolean verificaLogin(String nomeTabela, String email, String senha) {
        String sql = "SELECT * FROM " + nomeTabela + " WHERE c_email = ? AND c_senha = ?";
        boolean loginValido = false;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loginValido = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao verificar login: " + e.getMessage());
        }

        return loginValido;
    }


    // Verificar a quantidade de seguros que o cliente já possui
    public static int verificarQuantidadeSegurosCliente(String cpf) {
        String sql = "SELECT COUNT(*) AS total_seguros FROM T_SEGURO_AUTO WHERE c_cpf = ?";
        int totalSeguros = 0;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cpf);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalSeguros = resultSet.getInt("total_seguros");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao verificar seguros do cliente: " + e.getMessage());
        }

        return totalSeguros;
    }


    //Fazer uma consulta no banco e retornar os resultados
    public static List<Object[]> executarConsulta(String sql, Object[] parametros) {
        List<Object[]> resultados = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                preparedStatement.setObject(i + 1, parametros[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int numeroColunas = metaData.getColumnCount();

                while (resultSet.next()) {
                    Object[] linha = new Object[numeroColunas];
                    for (int i = 0; i < numeroColunas; i++) {
                        linha[i] = resultSet.getObject(i + 1);
                    }
                    resultados.add(linha);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao fazer consulta: " + e.getMessage());
        }

        return resultados;
    }


    // Dar um update no banco de dados
    public static boolean executarUpdate(String tabela, String[] colunas, Object[] valores, String condicao, Object[] parametrosCondicao) {
        StringBuilder sql = new StringBuilder("UPDATE " + tabela + " SET ");
        for (int i = 0; i < colunas.length; i++) {
            sql.append(colunas[i]).append(" = ?");
            if (i < colunas.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE ").append(condicao);

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < valores.length; i++) {
                preparedStatement.setObject(i + 1, valores[i]);
            }

            for (int i = 0; i < parametrosCondicao.length; i++) {
                preparedStatement.setObject(colunas.length + i + 1, parametrosCondicao[i]);
            }

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao executar update na tabela " + tabela + ": " + e.getMessage());
            return false;
        }
    }


    //Buscar um cliente pelo CPF
    public static Cliente buscarClientePorCpf(String cpf) {
        String sql = "SELECT * FROM T_CLIENTE WHERE c_cpf = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cpf);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("c_email");
                    String senha = resultSet.getString("c_senha");
                    return new Cliente(cpf, email, senha);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar cliente pelo CPF: " + e.getMessage());
        }
        return null;
    }


    //Ver se o cliente já existe pelo CPF
    public static boolean clienteExiste(String tabela, String cpf) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela + " WHERE c_cpf = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cpf);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao verificar existência do cliente: " + e.getMessage());
        }
        return false;
    }


    //Deletar uma entrada do banco
    public static boolean executarDelete(String tabela, String condicao, Object[] parametrosCondicao) {
        String sqlDelete = "DELETE FROM " + tabela + " WHERE " + condicao;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {

            for (int i = 0; i < parametrosCondicao.length; i++) {
                preparedStatement.setObject(i + 1, parametrosCondicao[i]);
            }

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover entrada" + tabela + ": " + e.getMessage());
            return false;
        }
    }
}
