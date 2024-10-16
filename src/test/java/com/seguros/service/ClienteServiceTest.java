package com.seguros.service;

import com.seguros.dao.ClienteDao;
import com.seguros.model.Cliente;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceTest {
    private ClienteService clienteService;
    private Connection connection;
    ClienteDao clienteDao = new ClienteDao();

    //Conexão com o banco
    @BeforeAll
    public void setup() throws SQLException {
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String user = "RM556071";
        String password = "090298";
        connection = DriverManager.getConnection(url, user, password);
        clienteService = new ClienteService(clienteDao);
    }


    //Fechar conexão após os testes
    @AfterAll
    public void teardown() throws SQLException {
        clienteDao.removerClientePorCpf("12345678905");
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    //Limpar o banco antes de cada teste
    @BeforeEach
    public void limparBase() {
        clienteDao.removerClientePorCpf("12345678901");
        clienteDao.removerClientePorCpf("12345678902");
        clienteDao.removerClientePorCpf("12345678903");
        clienteDao.removerClientePorCpf("12345678904");
    }


    //Testes
    @Test
    public void testCadastrarClienteComSucesso() {
        boolean sucesso = clienteService.cadastrarCliente("12345678901", "cliente@teste.com", "senha123");
        assertTrue(sucesso, "O cliente deveria ser cadastrado com sucesso");
    }

    @Test
    public void testCadastrarClienteComSenhaInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.cadastrarCliente("12345678902", "cliente2@teste.com", "senha"); // Senha inválida
        });
        assertEquals("A senha deve conter pelo menos 8 caracteres.", exception.getMessage());
    }

    @Test
    public void testCadastrarClienteComEmailInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.cadastrarCliente("12345678903", "email_invalido", "senha123"); // Email inválido
        });
        assertEquals("Email inválido.", exception.getMessage());
    }

    @Test
    public void testCadastrarClienteJaExistente() {
        clienteService.cadastrarCliente("12345678904", "cliente_existente@teste.com", "senha123");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.cadastrarCliente("12345678904", "cliente_existente@teste.com", "senha123"); // CPF duplicado
        });
        assertEquals("Cliente já cadastrado com esse CPF.", exception.getMessage());
    }

    @Test
    public void testRealizarLoginComSucesso() {
        clienteService.cadastrarCliente("12345678906", "login@teste.com", "senha123");
        boolean sucesso = clienteService.realizarLogin("login@teste.com", "senha123");
        assertTrue(sucesso, "O login deveria ser bem-sucedido");
    }

    @Test
    public void testRealizarLoginComCredenciaisInvalidas() {
        boolean loginFalhou = clienteService.realizarLogin("login@teste.com", "senha_errada");
        assertFalse(loginFalhou, "O login deveria falhar com credenciais inválidas");
    }

    @Test
    public void testAlterarSenhaComSucesso() {
        clienteService.cadastrarCliente("12345678905", "login@teste.com", "senha123");
        Cliente cliente = clienteService.buscarClientePorCpf("12345678905");
        boolean sucesso = clienteService.alterarSenha(cliente, "senha123", "novaSenha123");
        assertTrue(sucesso, "A senha deveria ser alterada com sucesso");
    }

    @Test
    public void testAlterarSenhaComSenhaAtualIncorreta() {
        Cliente cliente = clienteService.buscarClientePorCpf("12345678905");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.alterarSenha(cliente, "senha_errada", "novaSenha123");
        });
        assertEquals("Senha atual incorreta ou nova senha inválida.", exception.getMessage());
    }

    @Test
    public void testAlterarEmailComSucesso() {
        Cliente cliente = clienteService.buscarClientePorCpf("12345678905");
        boolean sucesso = clienteService.alterarEmail(cliente, "novoemail@teste.com");
        assertTrue(sucesso, "O email deveria ser alterado com sucesso");
    }

    @Test
    public void testAlterarEmailComEmailInvalido() {
        Cliente cliente = clienteService.buscarClientePorCpf("12345678905");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.alterarEmail(cliente, "email_invalido");
        });
        assertEquals("Email inválido.", exception.getMessage());
    }
}
