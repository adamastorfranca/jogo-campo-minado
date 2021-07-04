package br.com.adamastor.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.adamastor.cm.excecao.ExplosaoException;
import br.com.adamastor.cm.excecao.SairException;
import br.com.adamastor.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner scanner = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			while(continuar) {
				cicloDoJogo();			
				System.out.print("\nOutra partida? (S/n) ");
				String resposta = scanner.next();
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("\nTchau!!!");
		} finally {
			scanner.close();
		}
	}

	private void cicloDoJogo() {
		try {
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println("\n" + tabuleiro);		
				String digitado = capturarValorDigitado("Digite (x/y) ou (sair): ");
				Iterator<Integer> xy = Arrays
						.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim()))
						.iterator();
				digitado = capturarValorDigitado("(1) para Abrir ou (2) para Des/Marcar: ");
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}
			System.out.println("\n" + tabuleiro);
			System.out.println("Você ganhou!!!");
		} catch (ExplosaoException e) {
			System.out.println("\n" + tabuleiro);
			System.out.println("Você perdeu!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = scanner.next();
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
}
