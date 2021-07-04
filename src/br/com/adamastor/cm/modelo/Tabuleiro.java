package br.com.adamastor.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.adamastor.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int qntLinhas;
	private int qntColunas;
	private int qntMinas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int qntLinhas, int qntColunas, int qntMinas) {
		this.qntLinhas = qntLinhas;
		this.qntColunas = qntColunas;
		this.qntMinas = qntMinas;	
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.stream()
				  .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				  .findFirst()
				  .ifPresent(c -> c.abrir());;
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}	
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst().ifPresent(c -> c.alternarMarcacao());;
	}

	private void gerarCampos() {
		for (int linha = 0; linha < qntLinhas; linha++) {
			for (int coluna = 0; coluna < qntColunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
		}
	}
	
	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(c -> c.isMinado()).count();
		} while(minasArmadas < qntMinas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(campo -> campo.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int c = 0; c < qntColunas; c++) {
			sb.append(" " + c + " ");
		}
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < qntLinhas; l++) {
			sb.append(l + " ");
			for (int c = 0; c < qntColunas; c++) {
				sb.append(campos.get(i));
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public int getQntLinhas() {
		return qntLinhas;
	}

	public int getQntColunas() {
		return qntColunas;
	}
}
