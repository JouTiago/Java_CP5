package com.seguros.controller;

import com.seguros.model.Cliente;
import com.seguros.service.ClienteService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cliente")
public class ClienteController {
    private final ClienteService clienteService = new ClienteService();


    //Endpoint para fazer o cadastro
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarCliente(Cliente cliente) {
        try {
            boolean sucesso = clienteService.cadastrarCliente(cliente);
            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity(cliente).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("Cliente já cadastrado").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    //Endpoint para fazer o login
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response realizarLogin(@FormParam("email") String email, @FormParam("senha") String senha){
        try {
            boolean sucesso = clienteService.realizarLogin(email,senha);
            if (sucesso) {
                return Response.ok("Login realizado com sucesso").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao realizar login" + e.getMessage()).build();
        }
    }

}
