package com.seguros.model;

import java.time.LocalDate;

//Classe anemica

public class SeguroAuto {
    private long id;
    private Cliente cliente;
    private Veiculo veiculo;
    private double premio;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean sinistroGrave;


    //Construtores
    public SeguroAuto(long id, Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, boolean sinistroGrave) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.sinistroGrave = sinistroGrave;
    }


    //Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public double getPremio() {
        return premio;
    }
    public void setPremio(double premio) {
        this.premio = premio;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isSinistroGrave() {
        return sinistroGrave;
    }
    public void setSinistroGrave(boolean sinistroGrave) {
        this.sinistroGrave = sinistroGrave;
    }

}
