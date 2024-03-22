package negocio;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.Test;

import java.util.List;

public class GerenciadoraContasTest {

	private GerenciadoraContas gerContas;
	
	
	
	@Test
	public void testTransfereValor() {
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, 200, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);
		
		boolean sucesso = gerContas.transfereValor(idConta01, 100, idConta02);
		
		assertTrue(sucesso);
		assertThat(conta02.getSaldo(), is(100.0));
		assertThat(conta01.getSaldo(), is(100.0));
	}
}
