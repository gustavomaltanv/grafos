package model;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private String rotulo;
    private List<Aresta> arestas;
    private Cor corVertice;

    public Vertice(String rotulo) {
        this.rotulo = rotulo;
        this.arestas = new ArrayList<>();
        this.corVertice = null;
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

    public Cor getCorVertice() {
        return corVertice;
    }

    public void setCorVertice(Cor corVertice) {
        this.corVertice = corVertice;
    }
}
