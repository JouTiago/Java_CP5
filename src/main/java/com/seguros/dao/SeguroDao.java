package com.seguros.dao;

import com.seguros.model.SeguroAuto;
import com.seguros.util.OperacoesSql;

public class SeguroDao {
    private static final String TABELA = "T_SEGURO_AUTO";
    private static final String[] COLUNAS = {"id", "c_cpf", "v_placa", "premio", "data_inicio", "data_fim", "sinistro_grave"};


    // Métodos

    //Cadastrar o seguro no banco de dados
    public boolean cadastrarSeguro(SeguroAuto seguroAuto) {
        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {
                seguroAuto.getId(),
                seguroAuto.getCliente().getCpf(),
                seguroAuto.getVeiculo().getPlaca(),
                seguroAuto.getPremio(),
                seguroAuto.getDataInicio(),
                seguroAuto.getDataFim(),
                seguroAuto.isSinistroGrave()
        };
        return OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }
}
