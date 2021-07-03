package br.com.adamastor.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.adamastor.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int linha; 
	private final int coluna; 
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
		
	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	boolean adicionarVizinho(Campo outroCampo) {
		boolean linhaDiferente = linha != outroCampo.linha;
		boolean colunaDiferente = coluna !=outroCampo.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int diferencaLinha = Math.abs(linha - outroCampo.linha);
		int diferencaColuna = Math.abs(coluna - outroCampo.coluna);
		int diferencaGeral = diferencaLinha + diferencaColuna;
		
		boolean vizinhoNormal = diferencaGeral == 1 && !diagonal;
		boolean vizinhoDiagonal = diferencaGeral == 2 && diagonal;
		
		if(vizinhoNormal || vizinhoDiagonal) {
			vizinhos.add(outroCampo);
			return true;
		} else {
			return false;
		}
	}

	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	boolean abrir() {
		if(!aberto && !marcado) {
			aberto = true;
			if(minado) {
				throw new ExplosaoException();
			}
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}	
	}
	
	void minar() {
		minado = true;
	}

}
