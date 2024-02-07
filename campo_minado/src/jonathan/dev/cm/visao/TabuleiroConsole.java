package jonathan.dev.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import jonathan.dev.cm.excecao.ExplosaoException;
import jonathan.dev.cm.excecao.SairException;
import jonathan.dev.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	private Tabuleiro tabuleiro;
	private Scanner input = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}
	
	private void executarJogo() {
		try {
			boolean continuar = true;
			while(continuar) {
				cicloDoJogo();
				
				System.out.println("Outra partida? (S/N)");
				String resposta = input.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
		}catch(SairException e) {
			System.out.println("Tchau");
		}finally {
			input.close();
		}
	}
	
	private void cicloDoJogo() {
		try {
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite (x,y): ");
			
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					.map(e->Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				}else if("2".equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());					
				};

			}
			System.out.println("Você ganhou");
		}catch(ExplosaoException e){
			System.out.println(tabuleiro);
			System.out.println("Você perdeu");
		}
	}
	
	private String capturarValorDigitado(String text) {
		System.out.println(text);
		String digitado = input.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		
		return digitado;
	}
}
