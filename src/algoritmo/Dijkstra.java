package algoritmo;

import model.Grafo;
import model.Vertice;
import model.Aresta;

import java.util.*;

public class Dijkstra {

    public void executar(Grafo grafo, Vertice origem) {
        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> anteriores = new HashMap<>();
        PriorityQueue<Vertice> fila = new PriorityQueue<>(Comparator.comparing(distancias::get));

        for (Vertice vertice : grafo.getVertices()) {
            distancias.put(vertice, Double.MAX_VALUE);
            anteriores.put(vertice, null);
        }
        distancias.put(origem, 0.0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            Vertice verticeAtual = fila.poll();

            for (Aresta aresta : grafo.getArestas()) {
                if (aresta.getOrigem().equals(verticeAtual)) {
                    Vertice vizinho = aresta.getDestino();
                    double novaDistancia = distancias.get(verticeAtual) + aresta.getPeso();

                    if (novaDistancia < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDistancia);
                        anteriores.put(vizinho, verticeAtual);
                        fila.add(vizinho);
                    }
                }
            }
        }

        System.out.println("Distâncias a partir do vértice " + origem.getRotulo() + ":");
        for (Vertice vertice : distancias.keySet()) {
            System.out.println("Até " + vertice.getRotulo() + " : " + distancias.get(vertice));
        }
    }
}
