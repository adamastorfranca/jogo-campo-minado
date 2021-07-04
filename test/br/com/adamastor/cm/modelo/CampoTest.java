package br.com.adamastor.cm.modelo;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.adamastor.cm.excecao.ExplosaoException;

public class CampoTest {
	
	private Campo campo;
	
	@BeforeEach
	void inicializaCampo() {
		campo = new Campo(2, 2);
	}
	
	@Test
	void confirmarSeSaoVizinhos() {
		Campo superior = new Campo(1, 2);
		boolean resultadoS = campo.adicionarVizinho(superior);
		Campo inferior = new Campo(3, 2);
		boolean resultadoI = campo.adicionarVizinho(inferior);
		Campo esquerda = new Campo(2, 1);
		boolean resultadoE = campo.adicionarVizinho(esquerda);
		Campo direita = new Campo(2, 3);
		boolean resultadoD = campo.adicionarVizinho(direita);
		Campo superiorEsquerda = new Campo(1, 1);
		boolean resultadoSE = campo.adicionarVizinho(superiorEsquerda);
		Campo superiorDireita = new Campo(1, 3);
		boolean resultadoSD = campo.adicionarVizinho(superiorDireita);
		Campo inferiorEsquerda = new Campo(3, 1);
		boolean resultadoIE = campo.adicionarVizinho(inferiorEsquerda);
		Campo inferiorDireita = new Campo(3, 3);
		boolean resultadoID = campo.adicionarVizinho(inferiorDireita);

		Assertions.assertTrue(
				resultadoS && resultadoI && resultadoE && resultadoD &&
				resultadoSE && resultadoSD && resultadoIE && resultadoID);
	}
		
	@Test
	void confirmarSeNaoSaoVizinho() {
		Campo superior = new Campo(0, 2);
		boolean resultadoS = campo.adicionarVizinho(superior);
		Campo inferior = new Campo(4, 2);
		boolean resultadoI = campo.adicionarVizinho(inferior);
		Campo esquerda = new Campo(2, 0);
		boolean resultadoE = campo.adicionarVizinho(esquerda);
		Campo direita = new Campo(2, 4);
		boolean resultadoD = campo.adicionarVizinho(direita);
		Campo superiorEsquerda = new Campo(0, 0);
		boolean resultadoSE = campo.adicionarVizinho(superiorEsquerda);
		Campo superiorDireita = new Campo(0, 4);
		boolean resultadoSD = campo.adicionarVizinho(superiorDireita);
		Campo inferiorEsquerda = new Campo(4, 0);
		boolean resultadoIE = campo.adicionarVizinho(inferiorEsquerda);
		Campo inferiorDireita = new Campo(4, 4);
		boolean resultadoID = campo.adicionarVizinho(inferiorDireita);

		Assertions.assertFalse(
				resultadoS && resultadoI && resultadoE && resultadoD &&
				resultadoSE && resultadoSD && resultadoIE && resultadoID);
	}

	@Test
	void campoNaoMarcado() {
		Assert.assertFalse(campo.isMarcado());
	}
	
	@Test
	void campoMarcado() {
		campo.alternarMarcacao();
		Assert.assertTrue(campo.isMarcado());
	}
	
	@Test
	void campoMarcadoDuasVezes() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		Assert.assertFalse(campo.isMarcado());
	}
	
	@Test
	void campoNaoAberto() {
		Assert.assertFalse(campo.isAberto());
	}
	
	@Test
	void campoNaoMinado() {
		Assert.assertFalse(campo.isMinado());
	}
		
	@Test
	void abrindoCampoComMina() {
		campo.minar();
		Assert.assertThrows(ExplosaoException.class, () -> campo.abrir());
	}
	
	@Test
	void abrindoNaoMarcadoNaoMinadoNaoAberto() {
		Assert.assertTrue(campo.abrir());
	}
	
	@Test
	void tentarAbrirMarcadoNaoMinadoNaoAberto() {
		campo.alternarMarcacao();
		Assert.assertFalse(campo.abrir());
	}
	
	@Test
	void tentarAbrirMarcadoMinadoNaoAberto() {
		campo.alternarMarcacao();
		campo.minar();
		Assert.assertFalse(campo.abrir());
	}
	
	@Test
	void tentarAbrirNaoMarcadoNaoMinadoJaAberto() {
		campo.abrir();
		Assert.assertFalse(campo.abrir());
	}
	
	@Test
	void tentarAbrirMarcadoNaoMinadoJaAberto() {
		campo.alternarMarcacao();
		campo.abrir();
		Assert.assertFalse(campo.abrir());
	}
	
	@Test
	void abrindoNaoMarcadoNaoMinadoNaoAbertoComVizinhos() {
		Campo vizinho = new Campo(1, 2);
		Campo vizinhoDoVizinho = new Campo(0, 2);
		campo.adicionarVizinho(vizinho);
		vizinho.adicionarVizinho(vizinhoDoVizinho);
		campo.abrir();		
		Assert.assertTrue(vizinhoDoVizinho.isAberto() && vizinho.isAberto());
	}
	
	@Test
	void tentarAbrirNaoMarcadoNaoMinadoNaoAbertoComVizinhoComMina() {
		Campo vizinho = new Campo(1, 2);
		Campo vizinhoMinado = new Campo(1, 3);
		Campo vizinhoDoVizinho = new Campo(0, 2);
		vizinhoMinado.minar();
		campo.adicionarVizinho(vizinho);
		vizinho.adicionarVizinho(vizinhoDoVizinho);
		vizinho.adicionarVizinho(vizinhoMinado);
		campo.abrir();		
		Assert.assertFalse(vizinhoDoVizinho.isAberto());
	}
}
