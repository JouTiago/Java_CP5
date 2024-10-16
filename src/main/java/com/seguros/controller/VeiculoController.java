package com.seguros.controller;

import com.seguros.model.Cliente;
import com.seguros.model.Veiculo;
import com.seguros.service.VeiculoService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

@Path("/veiculo")
public class VeiculoController {
    private final VeiculoService veiculoService = new VeiculoService();


    //Endpoint para cadastrar o veículo
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarVeiculo(
            @FormParam("placa") String placa,
            @FormParam("cnhRegistro") String cnhRegistro,
            @FormParam("cnhDataEmissao") String cnhDataEmissao,
            @FormParam("fabricante") String fabricante,
            @FormParam("modelo") String modelo,
            @FormParam("ano") int ano,
            @FormParam("cpfCliente") String cpfCliente
    ) {
        try {
            Cliente cliente = new Cliente(cpfCliente, null, null);
            Veiculo veiculo = new Veiculo(placa, cnhRegistro, LocalDate.parse(cnhDataEmissao), fabricante, modelo, ano, cliente);

            boolean sucesso = veiculoService.cadastrarVeiculo(veiculo);
            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity(veiculo).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o veículo").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
