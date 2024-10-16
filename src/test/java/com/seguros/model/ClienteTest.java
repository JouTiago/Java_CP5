package com.seguros.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ClienteTest {

    //Testes
    @Test
    public void testValidarSenhaCorreta() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha1234");
        assertTrue(cliente.validarSenha(), "A senha deveria ser válida");
    }

    @Test
    public void testValidarSenhaInvalida() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "abc");
        assertFalse(cliente.validarSenha(), "A senha deveria ser inválida, pois é muito curta");
    }

    @Test
    public void testValidarSenhaSemNumero() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "abcdefgh");
        assertFalse(cliente.validarSenha(), "A senha deveria ser inválida, pois não contém números");
    }

    @Test
    public void testValidarEmailCorreto() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha123");
        assertTrue(cliente.validarEmail(), "O email deveria ser válido");
    }

    @Test
    public void testValidarEmailIncorreto() {
        Cliente cliente = new Cliente("12345678901", "clienteinvalido.com", "senha123");
        assertFalse(cliente.validarEmail(), "O email deveria ser inválido");
    }

    @Test
    public void testAlterarSenhaComSucesso() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha1234");

        boolean alterada = cliente.alterarSenha("senha1234", "novaSenha123");
        assertTrue(alterada, "A senha deveria ser alterada com sucesso");
        assertEquals("novaSenha123", cliente.getSenha(), "A senha deveria ser atualizada");
    }

    @Test
    public void testAlterarSenhaComErro() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha1234");

        boolean alterada = cliente.alterarSenha("senhaErrada", "novaSenha123");
        assertFalse(alterada, "A senha não deveria ser alterada, pois a senha atual está incorreta");
        assertEquals("senha1234", cliente.getSenha(), "A senha não deveria ser alterada");
    }

    @Test
    public void testAlterarEmailComSucesso() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha123");

        boolean alterado = cliente.alterarEmail("novoemail@teste.com");
        assertTrue(alterado, "O email deveria ser alterado com sucesso");
        assertEquals("novoemail@teste.com", cliente.getEmail(), "O email deveria ser atualizado");
    }

    @Test
    public void testAlterarEmailInvalido() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha123");

        boolean alterado = cliente.alterarEmail("emailinvalido.com");
        assertFalse(alterado, "O email não deveria ser alterado, pois é inválido");
        assertEquals("cliente@teste.com", cliente.getEmail(), "O email não deveria ser alterado");
    }
}