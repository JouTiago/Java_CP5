package com.seguros.model;

import java.time.LocalDate;

// Classe anemica

public class Sinistro {
    private long id;
    private String cpfCliente;
    private String placaVeiculo;
    private LocalDate dataSinistro;
    private String gravidade;
    private String descricao;


    // Construtores
    public Sinistro(long id, String cpfCliente, String placaVeiculo, LocalDate dataSinistro, String gravidade, String descricao) {
        this.id = id;
        this.cpfCliente = cpfCliente;
        this.placaVeiculo = placaVeiculo;
        this.dataSinistro = dataSinistro;
        this.gravidade = gravidade;
        this.descricao = descricao;
    }


    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }

    public String getPlacaVeiculo() { return placaVeiculo; }
    public void setPlacaVeiculo(String placaVeiculo) { this.placaVeiculo = placaVeiculo; }

    public LocalDate getDataSinistro() { return dataSinistro; }
    public void setDataSinistro(LocalDate dataSinistro) { this.dataSinistro = dataSinistro; }

    public String getGravidade() { return gravidade; }
    public void setGravidade(String gravidade) { this.gravidade = gravidade; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
