package it.polito.tdp.imdb.model;

public class RegistiAdiacenti implements Comparable<RegistiAdiacenti>{

	private Director d;
	private int peso;
	
	public RegistiAdiacenti(Director d, int peso) {
		super();
		this.d = d;
		this.peso = peso;
	}

	public Director getD() {
		return d;
	}

	public void setD(Director d) {
		this.d = d;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(RegistiAdiacenti o) {
		// TODO Auto-generated method stub
		return -(this.getPeso()-o.getPeso());
	}

	@Override
	public String toString() {
		return d + " - # attori condivisi: " + peso ;
	}
	
	
	
}
