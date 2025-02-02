package algoritmo;

import model.Aresta;
import model.Cor;
import model.Grafo;
import model.Vertice;

import java.util.*;

public class Coloracao {

    public void coloracaoVertices(Grafo grafo) {
        for (Vertice vertice : grafo.getVertices()) {
            Set<Cor> coresAdjacentes = new HashSet<>();

            // Considera as cores dos vértices adjacentes (de entrada e saída)
            for (Aresta aresta : vertice.getArestas()) {
                Vertice adjacente = aresta.getDestino().equals(vertice) ? aresta.getOrigem() : aresta.getDestino();
                if (adjacente.getCorVertice() != null) {
                    coresAdjacentes.add(adjacente.getCorVertice());
                }
            }

            // Adiciona as cores dos vértices com arestas de entrada (se o grafo for direcionado)
            for (Aresta aresta : grafo.getArestas()) {
                if (aresta.getDestino().equals(vertice)) {
                    Vertice adjacente = aresta.getOrigem();
                    if (adjacente.getCorVertice() != null) {
                        coresAdjacentes.add(adjacente.getCorVertice());
                    }
                }
            }

            vertice.setCorVertice(encontrarMenorCorDisponivel(coresAdjacentes));
        }
    }

    public void coloracaoArestas(Grafo grafo) {
        for (Aresta aresta : grafo.getArestas()) {
            Set<Cor> coresAdjacentes = new HashSet<>();
            for (Vertice vertice : grafo.getVertices()) {
                for (Aresta adjacente : vertice.getArestas()) {
                    if (adjacente.equals(aresta)) continue;
                    if (adjacente.getCorAresta() != null) {
                        coresAdjacentes.add(adjacente.getCorAresta());
                    }
                }
            }
            aresta.setCorAresta(encontrarMenorCorDisponivel(coresAdjacentes));
        }
    }

    private Cor encontrarMenorCorDisponivel(Set<Cor> coresAdjacentes) {
        for (Cor cor : Cor.values()) {
            if (!coresAdjacentes.contains(cor)) {
                return cor;
            }
        }
        return Cor.WHITE;
    }
}
