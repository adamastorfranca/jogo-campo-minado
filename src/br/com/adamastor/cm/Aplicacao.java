package br.com.adamastor.cm;

import java.util.Scanner;

import br.com.adamastor.cm.modelo.Tabuleiro;
import br.com.adamastor.cm.visao.TabuleiroConsole;

public class Aplicacao {
	
	public static void main(String[] args) {
		
		System.out.println("--- JOGO CAMPO MINADO ---");
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\nQuantas linhas: ");
		int qntLinhas = sc.nextInt();
		System.out.print("Quantas colunas: ");
		int qntColunas = sc.nextInt();
		System.out.print("Quantas minas: ");
		int qntMinas = sc.nextInt();
		
		Tabuleiro tabuleiro = new Tabuleiro(qntLinhas, qntColunas, qntMinas);	
		new TabuleiroConsole(tabuleiro);
		
		sc.close();
	}
}
