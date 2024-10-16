package com.seguros.dao;

import com.seguros.model.Sinistro;
import com.seguros.util.OperacoesSql;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SinistroDao {
    private static final String TABELA = "T_SINISTRO";
    private static final String[] COLUNAS = {"id", "c_cpf", "v_placa", "data_sinistro", "gravidade", "descricao"};


    // Inserir um novo sinistro no banco de dados
    public boolean cadastrarSinistro(Sinistro sinistro) {
        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {
                sinistro.getId(),
                sinistro.getCpfCliente(),
                sinistro.getPlacaVeiculo(),
                Date.valueOf(sinistro.getDataSinistro()),
                sinistro.getGravidade(),
                sinistro.getDescricao()
        };
        return OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }


    // Buscar sinistros graves de um cliente nos últimos 3 anos
    public List<Sinistro> buscarSinistrosGraves(String cpfCliente) {
        List<Sinistro> sinistros = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " WHERE c_cpf = ? AND gravidade = 'grave' AND data_sinistro >= ?";
        LocalDate tresAnosAtras = LocalDate.now().minusYears(3);

        List<Object[]> resultados = OperacoesSql.executarConsulta(sql, new Object[]{cpfCliente, Date.valueOf(tresAnosAtras)});

        for (Object[] linha : resultados) {
            Sinistro sinistro = new Sinistro(
                    (long) linha[0], // id
                    (String) linha[1], // c_cpf
                    (String) linha[2], // v_placa
                    ((Date) linha[3]).toLocalDate(), // data_sinistro
                    (String) linha[4], // gravidade
                    (String) linha[5] // descricao
            );
            sinistros.add(sinistro);
        }

        return sinistros;
    }
}
