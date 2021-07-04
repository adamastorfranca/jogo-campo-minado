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
	
	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public boolean isMinado() {
		return minado;
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

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "[x]";
		} else if (aberto && minado) {
			return "[*]";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return "[" + Long.toString(minasNaVizinhanca()) + "]";
		} else if (aberto) {
			return "[.]";
		} else {
			return "[_]";
		}
	}
}
