package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	ImdbDAO dao;
	Graph<Director,DefaultWeightedEdge> grafo;
	Map<Integer,Director> idMap;
	List<Director> soluzione;
	int best;
	
	public Model() {
		dao=new ImdbDAO();
	}
	
	public void creaGrafo(int anno) {
	idMap=new HashMap<>();
	grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	dao.listAllDirectors(idMap);
	List<Director> result=dao.getDirectors(anno, idMap);
	Collections.sort(result);
	Graphs.addAllVertices(grafo, result);
	
	for(Adiacenza a: dao.getAdiacenza(anno, idMap)) {
		Graphs.addEdge(grafo, a.getD1(), a.getD2(), a.getPeso());
	}
	}
	
	public int getVertexSize() {
		return this.grafo.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return this.grafo.edgeSet().size();
	}
	public List<Director> getDirettori(){
		List<Director> result=new ArrayList<>(grafo.vertexSet());
		Collections.sort(result);
		return result;
	}
	
	public List<RegistiAdiacenti> getAdiacenze(Director partenza){
		List<RegistiAdiacenti> direttori= new ArrayList<>();
		for(Director d:Graphs.neighborListOf(grafo, partenza)) {
			direttori.add(new RegistiAdiacenti(d,(int)grafo.getEdgeWeight(grafo.getEdge(d, partenza))));
		}
		Collections.sort(direttori);
		return direttori;
	}
	
	public List<Director> getCammino(int c,Director partenza){
		soluzione=new ArrayList<Director>();
		best=0;
		int tot=0;
		List<Director> parziale=new ArrayList<Director>();
		parziale.add(partenza);
		cercaSoluzione(parziale,c,tot,1);
		return soluzione;
	}

	private void cercaSoluzione(List<Director> parziale, int c, int tot,int L) {
		//caso terminale
		if(tot>c) {
			return ;
		}else if(parziale.size()>best) {
			best=parziale.size();
			soluzione=new ArrayList<>(parziale);
		}
		if(L==grafo.vertexSet().size())
			return ;
		
		List<RegistiAdiacenti>disponibili=this.getAdiacenze(parziale.get(parziale.size()-1));
			for(RegistiAdiacenti d: disponibili) {
				if(d.getPeso()<=c && !parziale.contains(d.getD())) {
					parziale.add(d.getD());
					cercaSoluzione(parziale,c,tot+d.getPeso(),L+1);
					//backTracking
					parziale.remove(d.getD());
				
			}
		}
		
		
	}
}
