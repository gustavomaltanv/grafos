package model;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private String nome;
    private boolean direcionado;
    private List<Vertice> vertices;
    private List<Aresta> arestas;

    public Grafo(String nome, boolean direcionado) {
        this.nome = nome;
        this.direcionado = direcionado;
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void addVertice(Vertice vertice) {
        this.vertices.add(vertice);
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void addAresta(Aresta aresta) {
        this.arestas.add(aresta);
        aresta.getOrigem().adicionarAresta(aresta);
        if(!this.direcionado) {
            aresta.getDestino().adicionarAresta(new Aresta(aresta.getDestino(), aresta.getOrigem(), aresta.getPeso()));
        }
    }

    public void removerAresta(Aresta aresta) {
        arestas.remove(aresta);
        aresta.getOrigem().removerAresta(aresta);
    }

    public Aresta encontrarAresta(String origem, String destino) {
        return arestas.stream().filter(a -> a.getOrigem().getRotulo().equals(origem) &&
                a.getDestino().getRotulo().equals(destino)).findFirst().orElse(null);
    }

    public int getOrdem() {
        return arestas.size();
    }

    public int getGrau(Vertice vertice) {
        if(this.direcionado) {
            return (int) this.arestas.stream().filter(a -> a.getDestino().equals(vertice)
                    || a.getOrigem().equals(vertice) ).count();
        }
        else {
            return vertice.getArestas().size();
        }
    }
}
