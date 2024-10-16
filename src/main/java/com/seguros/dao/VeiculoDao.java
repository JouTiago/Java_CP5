package com.seguros.dao;

import com.seguros.model.Veiculo;
import com.seguros.util.OperacoesSql;

public class VeiculoDao {
    private static final String TABELA = "T_VEICULO";
    private static final String[] COLUNAS = {"v_placa", "v_cnh_registro",
            "v_cnh_data", "v_fabricante", "v_modelo", "v_ano", "c_cpf"};


    //Métodos

    //Cadastrar o veículo no banco de dados
    public boolean cadastrarVeiculo(Veiculo veiculo) {
        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {
                veiculo.getPlaca(),
                veiculo.getCnhRegistro(),
                veiculo.getCnhDataEmissao(),
                veiculo.getFabricante(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getCliente().getCpf()
        };
        return OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

}
