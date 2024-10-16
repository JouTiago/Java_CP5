package com.seguros.model;

import java.time.LocalDate;
import java.time.Period;

public class Veiculo {
    private String placa;
    private String cnhRegistro;
    private LocalDate cnhDataEmissao;
    private String fabricante;
    private String modelo;
    private int ano;
    private Cliente cliente;


    //Construtores
    public Veiculo(String placa, String cnhRegistro, LocalDate cnhDataEmissao, String fabricante, String modelo, int ano, Cliente cliente) {
        this.placa = placa;
        this.cnhRegistro = cnhRegistro;
        this.cnhDataEmissao = cnhDataEmissao;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.ano = ano;
        this.cliente = cliente;
    }


    // Getters e Setters
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public String getCnhRegistro() {
        return cnhRegistro;
    }
    public void setCnhRegistro(String cnhRegistro) {
        this.cnhRegistro = cnhRegistro;
    }


    public LocalDate getCnhDataEmissao() {
        return cnhDataEmissao;    }

    public void setCnhDataEmissao(LocalDate cnhDataEmissao) {
        this.cnhDataEmissao = cnhDataEmissao;
    }


    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }


    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }


    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    //Métodos

    //Validação da placa no formato brasileiro (LLL9L99)
    public boolean validarPlaca() {
        return this.placa.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}");
    }


    //Validação da CNH
    public boolean validarCnh() {
        if (this.cnhRegistro == null || !this.cnhRegistro.matches("\\d{11}")) {
            return false;
        };

        LocalDate hoje = LocalDate.now();

        if (this.cnhDataEmissao == null || Period.between(this.cnhDataEmissao, hoje).getYears() > 30) {
            return false;
        }

        return true;
    }
}
