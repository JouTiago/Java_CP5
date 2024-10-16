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
    public Response cadastrarCliente(
            @FormParam("cpf") String cpf,
            @FormParam("email") String email,
            @FormParam("senha") String senha) {
        try {
            boolean sucesso = clienteService.cadastrarCliente(cpf, email, senha);
            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity("Cliente cadastrado com sucesso").build();
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
    public Response realizarLogin(
            @FormParam("email") String email,
            @FormParam("senha") String senha) {
        try {
            boolean sucesso = clienteService.realizarLogin(email, senha);
            if (sucesso) {
                return Response.ok("Login realizado com sucesso").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao realizar login: " + e.getMessage()).build();
        }
    }


    // Endpoint para alterar o email
    @PUT
    @Path("/alterar-email")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarEmail(
            @FormParam("cpf") String cpf,
            @FormParam("novoEmail") String novoEmail) {
        try {
            Cliente cliente = clienteService.buscarClientePorCpf(cpf);

            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado.").build();
            }

            boolean sucesso = clienteService.alterarEmail(cliente, novoEmail);
            if (sucesso) {
                return Response.ok("Email alterado com sucesso").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao alterar o email").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    // Endpoint para alterar a senha
    @PUT
    @Path("/alterar-senha")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarSenha(
            @FormParam("cpf") String cpf,
            @FormParam("senhaAtual") String senhaAtual,
            @FormParam("novaSenha") String novaSenha
    ) {
        try {
            Cliente cliente = new Cliente(cpf, null, senhaAtual);
            boolean sucesso = clienteService.alterarSenha(cliente, senhaAtual, novaSenha);

            if (sucesso) {
                return Response.ok("Senha alterada com sucesso").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao alterar a senha").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
