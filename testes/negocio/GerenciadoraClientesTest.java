package negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadoraClientesTest {
    private GerenciadoraClientes gerenciadoraClientes;
    private int idCliente01 = 1;
    private int idCliente02 = 2;

    @Before
    public void setUp() {
        /* ======== Montagem do Cenário ======== */
        // criando alguns clientes
        Cliente cliente01 = new Cliente(idCliente01, "João", 31, "joao@gmail.com", 1, true);
        Cliente cliente02 = new Cliente(idCliente02, "Maria", 34, "maria@gmail.com", 1, true);

        // inserindo os clientes criados na lista de clientes do banco
        List<Cliente> clientesDoBanco = new ArrayList<>();
        clientesDoBanco.add(cliente01);
        clientesDoBanco.add(cliente02);

        gerenciadoraClientes = new GerenciadoraClientes(clientesDoBanco);
    }

    @After
    public void tearDown() {
        // Limpa o cenário após cada teste
        gerenciadoraClientes.limpa();
    }

    /**
     * Teste básico da remoção de um cliente a partir do seu ID.
     */
    @Test
    public void testRemoveCliente() {
        /* ======== Execução ======== */
        boolean clienteRemovido = gerenciadoraClientes.removeCliente(idCliente02);

        /* ======== Verificações ======== */
        assertThat(clienteRemovido, is(true));
        assertThat(gerenciadoraClientes.getClientesDoBanco().size(), is(1));
        assertNull(gerenciadoraClientes.pesquisaCliente(idCliente02));
    }

    /**
     * Testes de validação de idade.
     */
    @Test(expected = IdadeNaoPermitidaException.class)
    public void testValidaIdadeAbaixoLimiteInferior() throws IdadeNaoPermitidaException {
        // menor que 18 anos
        gerenciadoraClientes.validaIdade(17);
    }

    @Test
    public void testValidaIdadeDentroIntervaloPermitido() {
        // entre 18 e 65 anos
        try {
            assertTrue(gerenciadoraClientes.validaIdade(25));
        } catch (IdadeNaoPermitidaException e) {
            assertFalse(true);
            // Este teste deve passar, portanto, a exceção não deve ser lançada
        }
    }

    @Test(expected = IdadeNaoPermitidaException.class)
    public void testValidaIdadeAcimaLimiteSuperior() throws IdadeNaoPermitidaException {
        // maior que 65 anos
        gerenciadoraClientes.validaIdade(66);
    }

    @Test
    public void testValidaIdadeLimiteInferior() {
        // Idade igual ao limite inferior (18 anos)
        try {
            assertTrue(gerenciadoraClientes.validaIdade(18));
        } catch (IdadeNaoPermitidaException e) {
            assertFalse(true);
            // não deve ser lançada
        }
    }

    @Test
    public void testValidaIdadeLimiteSuperior() {
        // Idade igual ao limite superior (65 anos)
        try {
            assertTrue(gerenciadoraClientes.validaIdade(65));
        } catch (IdadeNaoPermitidaException e) {
            assertFalse(true);
            // exceção não deve ser lançada
        }
    }

    @Test(expected = IdadeNaoPermitidaException.class)
    public void testValidaIdadeProximaLimiteInferior() throws IdadeNaoPermitidaException {
        // Idade próxima ao limite inferior (17 anos)
        gerenciadoraClientes.validaIdade(17);
    }

    @Test(expected = IdadeNaoPermitidaException.class)
    public void testValidaIdadeProximaLimiteSuperior() throws IdadeNaoPermitidaException {
        // Idade próxima ao limite superior (66 anos)
        gerenciadoraClientes.validaIdade(66);
    }
}
