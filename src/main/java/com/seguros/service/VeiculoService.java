package com.seguros.service;

import com.seguros.dao.VeiculoDao;
import com.seguros.model.Veiculo;

public class VeiculoService {
    private final VeiculoDao veiculoDao = new VeiculoDao();


    //Métodos

    //Cadastrar um veículo com validações
    public boolean cadastrarVeiculo(Veiculo veiculo) {

        //Validar a placa
        if (!veiculo.validarPlaca()) {
            throw new IllegalArgumentException("Placa inválida. A placa deve seguir o formato brasileiro");
        }

        //Validar a CNH
        if (!veiculo.validarCnh()) {
            throw new IllegalArgumentException("CNH inválida. O número de registro deve ter 11 dígitos");
        }

        //Realizar o cadastro
        return veiculoDao.cadastrarVeiculo(veiculo);
    }
}
