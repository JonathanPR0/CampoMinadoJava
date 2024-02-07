package jonathan.dev.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import jonathan.dev.cm.excecao.ExplosaoException;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
				.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst().ifPresent(c -> c.alternarMarcacao());
	}
	
	private void gerarCampos() {
		for(int l = 0; l < this.linhas; l++) {
			for(int c = 0; c < this.colunas; c++) {
				campos.add(new Campo(l, c));
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio =(int) (Math.random()* campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < this.minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  | ");
		for(int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		sb.append("_ | ");
		for(int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append("_");
			sb.append(" ");
		}
		sb.append("\n");
		int i = 0;
		for(int l = 0; l < this.linhas; l++) {
			sb.append(l);
			sb.append(" | ");
			for(int c = 0; c < this.colunas; c++) {
				sb.append(" "); 
				sb.append(this.campos.get(i)); 
				sb.append(" "); 
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
