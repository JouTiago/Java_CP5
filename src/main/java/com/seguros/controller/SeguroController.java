package com.seguros.controller;

import com.seguros.model.Cliente;
import com.seguros.model.Veiculo;
import com.seguros.service.SeguroService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;


@Path("/seguro")
public class SeguroController {
    private final SeguroService seguroAutoService = SeguroService.getInstance();


    // Endpoint para contratar um seguro
    @POST
    @Path("/contratar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response contratarSeguroAuto(
            @FormParam("cpfCliente") String cpfCliente,
            @FormParam("placaVeiculo") String placaVeiculo,
            @FormParam("dataInicio") String dataInicio,
            @FormParam("dataFim") String dataFim,
            @FormParam("cnhRegistro") String cnhRegistro,
            @FormParam("cnhDataEmissao") String cnhDataEmissao
    ) {
        try {
            Cliente cliente = new Cliente(cpfCliente, null, null);
            Veiculo veiculo = new Veiculo(placaVeiculo, cnhRegistro, LocalDate.parse(cnhDataEmissao), null, null, 0, cliente);

            boolean sucesso = seguroAutoService.contratarSeguroAuto(
                    cliente,
                    veiculo,
                    LocalDate.parse(dataInicio),
                    LocalDate.parse(dataFim)
            );

            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity("Seguro contratado com sucesso").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao contratar seguro").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

