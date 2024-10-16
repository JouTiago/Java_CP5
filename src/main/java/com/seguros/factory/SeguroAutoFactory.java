package com.seguros.factory;

import com.seguros.model.Cliente;
import com.seguros.model.SeguroAuto;
import com.seguros.model.Veiculo;
import java.time.LocalDate;

public class SeguroAutoFactory implements EntityFactory<SeguroAuto> {
    private long id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;


    //Construtores
    public SeguroAutoFactory(long id, Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }


    //Métodos

    @Override
    public SeguroAuto criar() {
        return new SeguroAuto(id, cliente, veiculo, dataInicio, dataFim, false);
    }
}
