package com.seguros.dao;

import com.seguros.model.Cliente;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteDaoTest {
    private ClienteDao clienteDao;
    private Connection connection;

    //Conexão com o banco
    @BeforeAll
    public void setup() throws SQLException {
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String user = "RM556071";
        String password = "090298";
        connection = DriverManager.getConnection(url, user, password);

        clienteDao = new ClienteDao();
    }

    //Fechar conexão após os testes
    @AfterAll
    public void teardown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    //Limpar o banco antes de cada teste
    @BeforeEach
    public void limparBase() {
        clienteDao.removerClientePorCpf("12345678901");
        clienteDao.removerClientePorCpf("98765432100");
    }


    //Testes
    @Test
    public void testCadastrarCliente() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha123");

        boolean cadastrado = clienteDao.cadastrarCliente(cliente);
        assertTrue(cadastrado, "O cliente deveria ser cadastrado com sucesso");
    }

    @Test
    public void testCadastrarClienteCpfExistente() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha123");

        boolean cadastradoPrimeiraVez = clienteDao.cadastrarCliente(cliente);
        assertTrue(cadastradoPrimeiraVez, "O cliente deveria ser cadastrado com sucesso");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteDao.cadastrarCliente(cliente);
        });
        assertEquals("Cliente já cadastrado com esse CPF.", exception.getMessage());
    }

    @Test
    public void testVerificarLogin() {
        Cliente cliente = new Cliente("12345678901", "cliente@teste.com", "senha123");
        clienteDao.cadastrarCliente(cliente);

        boolean loginValido = clienteDao.verificarLogin("cliente@teste.com", "senha123");
        assertTrue(loginValido, "O login deveria ser válido");

        boolean loginInvalido = clienteDao.verificarLogin("cliente@teste.com", "senhaErrada");
        assertFalse(loginInvalido, "O login deveria ser inválido");
    }

    @Test
    public void testBuscarPorCpf() {
        Cliente cliente = new Cliente("98765432100", "teste2@cliente.com", "senha321");
        clienteDao.cadastrarCliente(cliente);

        Cliente clienteBuscado = clienteDao.buscarPorCpf("98765432100");
        assertNotNull(clienteBuscado, "O cliente deveria ser encontrado pelo CPF");
        assertEquals("teste2@cliente.com", clienteBuscado.getEmail(), "O email do cliente deveria ser igual");
    }

    @Test
    public void testAtualizarCliente() {
        Cliente cliente = new Cliente("98765432100", "teste2@cliente.com", "senha321");
        clienteDao.cadastrarCliente(cliente);

        cliente.setEmail("novoemail@cliente.com");
        cliente.setSenha("novaSenha123");
        boolean atualizado = clienteDao.atualizarCliente(cliente);
        assertTrue(atualizado, "O cliente deveria ser atualizado com sucesso");

        Cliente clienteAtualizado = clienteDao.buscarPorCpf("98765432100");
        assertEquals("novoemail@cliente.com", clienteAtualizado.getEmail(), "O email deveria ser atualizado");
        assertEquals("novaSenha123", clienteAtualizado.getSenha(), "A senha deveria ser atualizada");
    }

    @Test
    public void testAtualizarClienteNaoExistente() {
        Cliente clienteNaoExistente = new Cliente("00000000000", "naoexiste@cliente.com", "senha000");

        boolean atualizado = clienteDao.atualizarCliente(clienteNaoExistente);
        assertFalse(atualizado, "O cliente não deveria ser atualizado, pois não existe no banco de dados");
    }

    @Test
    public void testRemoverClientePorCpf() {
        Cliente cliente = new Cliente("11111111111", "deletar@cliente.com", "senha123");
        clienteDao.cadastrarCliente(cliente);

        boolean removido = clienteDao.removerClientePorCpf("11111111111");
        assertTrue(removido, "O cliente deveria ser removido com sucesso");

        Cliente clienteRemovido = clienteDao.buscarPorCpf("11111111111");
        assertNull(clienteRemovido, "O cliente não deveria ser encontrado, pois foi removido");
    }

    @Test
    public void testRemoverClienteNaoExistente() {
        boolean removido = clienteDao.removerClientePorCpf("00000000000");
        assertFalse(removido, "O cliente não deveria ser removido, pois não existe no banco de dados");
    }

}