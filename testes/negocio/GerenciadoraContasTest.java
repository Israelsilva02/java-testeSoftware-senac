package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GerenciadoraContasTest {

    private GerenciadoraContas gerContas;

    @Test
    public void testTransferenciaSaldoSuficiente() {
        // Cenário
        int idConta01 = 1;
        int idConta02 = 2;
        ContaCorrente conta01 = new ContaCorrente(idConta01, 200, true);
        ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
        
        List<ContaCorrente> contasDoBanco = new ArrayList<>();
        contasDoBanco.add(conta01);
        contasDoBanco.add(conta02);
        
        gerContas = new GerenciadoraContas(contasDoBanco);

        // Execução
        boolean sucesso = gerContas.transfereValor(idConta01, 100, idConta02);

        // Verificações
        assertTrue(sucesso);
        assertThat(conta01.getSaldo(), is(100.0));
        assertThat(conta02.getSaldo(), is(100.0));
    }

    @Test
    public void testTransferenciaSaldoInsuficientePositivoConta1() {
        // Cenário
        int idConta01 = 1;
        int idConta02 = 2;
        ContaCorrente conta01 = new ContaCorrente(idConta01, 100, true);
        ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
        
        List<ContaCorrente> contasDoBanco = new ArrayList<>();
        contasDoBanco.add(conta01);
        contasDoBanco.add(conta02);
        
        gerContas = new GerenciadoraContas(contasDoBanco);

        // Execução
        boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);

        // Verificações
        assertTrue(sucesso);
        assertThat(conta01.getSaldo(), is(-100.0));
        assertThat(conta02.getSaldo(), is(200.0)); // Saldo da conta 02 não deve ter sido alterado
    }

    @Test
    public void testTransferenciaSaldoInsuficienteNegativoConta1() {
        // Cenário
        int idConta01 = 1;
        int idConta02 = 2;
        ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
        ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
        
        List<ContaCorrente> contasDoBanco = new ArrayList<>();
        contasDoBanco.add(conta01);
        contasDoBanco.add(conta02);
        
        gerContas = new GerenciadoraContas(contasDoBanco);

        // Execução
        boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);

        // Verificações
        assertTrue(sucesso);
        assertThat(conta01.getSaldo(), is(-300.0));
        assertThat(conta02.getSaldo(), is(200.0)); // Saldo da conta 02 não deve ter sido alterado
    }

    @Test
    public void testTransferenciaSaldoInsuficienteAmbasContas() {
        // Cenário
        int idConta01 = 1;
        int idConta02 = 2;
        ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
        ContaCorrente conta02 = new ContaCorrente(idConta02, -100, true);
        
        List<ContaCorrente> contasDoBanco = new ArrayList<>();
        contasDoBanco.add(conta01);
        contasDoBanco.add(conta02);
        
        gerContas = new GerenciadoraContas(contasDoBanco);

        // Execução
        boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);

        // Verificações
        assertTrue(sucesso);
        assertThat(conta01.getSaldo(), is(-300.0)); // Saldo da conta 01 não deve ter sido alterado
        assertThat(conta02.getSaldo(), is(100.0)); // Saldo da conta 02 não deve ter sido alterado
    }
}
