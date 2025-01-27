package model;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private String rotulo;
    private List<Aresta> arestas;

    public Vertice(String rotulo) {
        this.rotulo = rotulo;
        this.arestas = new ArrayList<>();
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void adicionarAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

    public void removerAresta(Aresta aresta) {
        this.arestas.remove(aresta);
    }
}
