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
        vertices.add(vertice);
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void addAresta(Aresta aresta) {
        arestas.add(aresta);
    }
}
