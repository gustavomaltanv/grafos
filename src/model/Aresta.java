package model;

public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private double peso;
    private Cor corAresta;

    public Aresta(Vertice origem, Vertice destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.corAresta = null;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Cor getCorAresta() {
        return corAresta;
    }

    public void setCorAresta(Cor corAresta) {
        this.corAresta = corAresta;
    }
}
