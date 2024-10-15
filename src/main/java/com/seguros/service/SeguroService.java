package com.seguros.service;

import com.seguros.dao.SeguroDao;
import com.seguros.model.Cliente;
import com.seguros.model.SeguroAuto;
import com.seguros.model.Veiculo;
import com.seguros.util.OperacoesSql;
import java.time.LocalDate;
import java.time.Period;

public class SeguroService {
    private final SeguroDao seguroAutoDao = new SeguroDao();


    //Métodos

    //Contratar o seguro
    public boolean contratarSeguroAuto(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {

        // Gerar novo ID para o seguro, associado a um cliente e a um veiculo
        int novoId = OperacoesSql.gerarNovoId("T_SEGURO_AUTO", "id");
        SeguroAuto seguroAuto = new SeguroAuto(novoId, cliente, veiculo, dataInicio, dataFim, false);


        //Regra de Negócio: Validar a CNH e a placa
        if (!veiculo.validarPlaca()) {
            throw new IllegalArgumentException("Placa inválida.");
        }
        if (!veiculo.validarCnh()) {
            throw new IllegalArgumentException("CNH inválida.");
        }

        // Regra de Negócio: Verifica a elegibilidade do cliente para contratar um seguro
        if (!seguroAuto.verificarElegibilidade()) {
            throw new IllegalArgumentException("Cliente não elegível para contratar o seguro.");
        }

        //Calcular o valor do premio sem desconto
        double premio = seguroAuto.getPremio();

        //Regra de Negócio: Ver se o cliente já possui seguros no banco. 10% de desconto para o segundo seguro e
        // 5% adicional no caso do cliete ter histórico limpo (sem sinistros graves)
        int quantidadeSeguros = OperacoesSql.verificarQuantidadeSegurosCliente(cliente.getCpf());

        if (quantidadeSeguros > 0) {
            premio *= 0.90;
        }
        if (historicoLimpo(seguroAuto)) {
            premio *= 0.95;
        }

        // Valor final do prêmio
        seguroAuto.setPremio(premio);


        // Cadastrar o seguro no banco
        return seguroAutoDao.cadastrarSeguro(seguroAuto);
    }


    //Regra de Negócio: Ver se o cliente tem um histórico sem sinistros graves nos últimos 3 anos
    private boolean historicoLimpo(SeguroAuto seguroAuto) {
        LocalDate hoje = LocalDate.now();
        return !seguroAuto.isSinistroGrave() && Period.between(seguroAuto.getDataInicio(), hoje).getYears() <= 3;
    }
}
