package jonathan.dev.cm;

import jonathan.dev.cm.modelo.Tabuleiro;
import jonathan.dev.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6,6,3);
		
		new TabuleiroConsole(tabuleiro);
	}
}
