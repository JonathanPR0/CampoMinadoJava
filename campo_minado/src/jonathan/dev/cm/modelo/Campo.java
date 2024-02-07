package jonathan.dev.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import jonathan.dev.cm.excecao.ExplosaoException;

public class Campo {
	private final int linha;
	private final int coluna;

	private boolean minado = false;
	private boolean aberto = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	
	public Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	};
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = this.linha != vizinho.linha;
		boolean colunaDiferente = this.coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(this.linha - vizinho.linha);
		int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
	}
	
	void alternarMarcacao() {
		if(!this.aberto) {
			this.marcado = !this.marcado;
		}
	}
	
	boolean abrir() {
		if(!this.aberto && !this.marcado) {
			this.aberto = true;
			if(this.minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v->v.abrir());
			}
			
			return true;
		}else {			
			return false;
		}
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		this.minado = true;
	}
	
	public boolean isMinado() {
		return this.minado;
	}
	
	public boolean isMarcado() {
		return this.marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public boolean isAberto() {
		return this.aberto;
	}
	
	public int getLinha() {
		return this.linha;
	}
	
	public int getColuna() {
		return this.coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !this.minado && this.aberto;
		boolean protegido = this.minado && this.marcado;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		this.aberto = false;
		this.minado = false;
		this.marcado = false;
	}
	
	@Override 
	public String toString() {
		if(this.marcado) {
			return "x";
		}else if(this.aberto && this.minado) {
			return "*";
		}else if(this.aberto && this.minasNaVizinhanca() > 0) {
			return Long.toString(this.minasNaVizinhanca());
		}else if(this.aberto) {
			return " ";
		}else {
			return "?";
		}
	}
}


