package com.seguros.service;

import com.seguros.dao.SeguroDao;
import com.seguros.factory.SeguroAutoFactory;
import com.seguros.dao.SinistroDao;
import com.seguros.model.Cliente;
import com.seguros.model.SeguroAuto;
import com.seguros.model.Sinistro;
import com.seguros.model.Veiculo;
import com.seguros.util.OperacoesSql;

import java.time.LocalDate;
import java.util.List;

// Implementação de Singleton

public class SeguroService {
    private static SeguroService instancia;
    private final SeguroDao seguroDao = new SeguroDao();
    private final SinistroDao sinistroDao = new SinistroDao();


    //Construtores

    private SeguroService() { }


    //Métodos

    //Retornar a única instância da classe
    public static synchronized SeguroService getInstance() {
        if (instancia == null) {
            instancia = new SeguroService();
        }
        return instancia;
    }


    //Contratar o seguro
    public boolean contratarSeguroAuto(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {


        // Gerar novo ID para o seguro, associado a um cliente e a um veiculo
        int novoId = OperacoesSql.gerarNovoId("T_SEGURO_AUTO", "id");

        SeguroAutoFactory seguroAutoFactory = new SeguroAutoFactory(novoId, cliente, veiculo, dataInicio, dataFim);
        SeguroAuto seguroAuto = seguroAutoFactory.criar();


        //Verifica a elegibilidade do cliente
        if (verificarElegibilidade(cliente, veiculo)) {
            throw new IllegalArgumentException("Cliente não elegível para contratar o seguro.");
        }


        //Calcular o valor do premio
        double premio = calcularPremio(cliente, veiculo);


        // Valor final do prêmio no seguro
        seguroAuto.setPremio(premio);


        // Cadastrar o seguro no banco
        return seguroDao.cadastrarSeguro(seguroAuto);
    }


    //Regra de Negócio: Ver se o cliente tem um histórico sem sinistros graves nos últimos 3 anos (simulação)
    private boolean historicoLimpo(Cliente cliente) {
        List<Sinistro> sinistrosGraves = sinistroDao.buscarSinistrosGraves(cliente.getCpf());
        return sinistrosGraves.isEmpty();
    }


    // Regra de Negócio: Calcular o premio com base no tipo do veículo e no ano
    // SUVs tem premio maior, Hatch menor, 10% de desconto para o segundo seguro e mais 5% se o cliente tiver histórico limpo
    private double calcularPremio(Cliente cliente, Veiculo veiculo) {
        double valorBase = veiculo.getAno() * 1000;

        if (veiculo.getModelo().equalsIgnoreCase("SUV")) {
            valorBase *= 1.2;
        } else if (veiculo.getModelo().equalsIgnoreCase("HATCH")) {
            valorBase *= 0.9;
        }

        // Aplicar a taxa base do seguro
        double premio = valorBase * 0.05;

        int quantidadeSeguros = OperacoesSql.verificarQuantidadeSegurosCliente(cliente.getCpf());

        if (quantidadeSeguros > 0) {
            premio *= 0.90;
        }

        if (historicoLimpo(cliente)) {
            premio *= 0.95;
        }

        return premio;
    }

    //Regra de Negócio: Verificar elegibilidade do cliente para contratar um seguro por veículo
    public boolean verificarElegibilidade(Cliente cliente, Veiculo veiculo) {

        //Validar a CNH e a placa
        if (!veiculo.validarPlaca()) {
            throw new IllegalArgumentException("Placa inválida.");
        }
        if (!veiculo.validarCnh()) {
            throw new IllegalArgumentException("CNH inválida.");
        }

        return cliente !=null;
    }

}
