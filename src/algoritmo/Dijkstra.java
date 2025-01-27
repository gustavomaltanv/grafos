package algoritmo;

import model.Grafo;
import model.Vertice;
import model.Aresta;

import java.util.*;

public class Dijkstra {

    public void executar(Grafo grafo, Vertice origem, Vertice destino) {
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

            for (Aresta aresta : verticeAtual.getArestas()) {
                Vertice vizinho = aresta.getDestino();
                double novaDistancia = distancias.get(verticeAtual) + aresta.getPeso();

                if (novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    anteriores.put(vizinho, verticeAtual);
                    fila.add(vizinho);
                }
            }
        }

        // Imprime o caminho e a distância até o destino
        if (distancias.get(destino) != Double.MAX_VALUE) {
            System.out.println("Caminho do vértice " + origem.getRotulo() + " até o vértice " + destino.getRotulo() + ":");
            List<Vertice> caminho = new LinkedList<>();
            for (Vertice vertice = destino; vertice != null; vertice = anteriores.get(vertice)) {
                caminho.add(vertice);
            }
            Collections.reverse(caminho);
            for (int i = 0; i < caminho.size(); i++) {
                System.out.print(caminho.get(i).getRotulo());
                if (i < caminho.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println(" - Distância: " + distancias.get(destino));
        } else {
            System.out.println("Não há caminho do vértice " + origem.getRotulo() + " até o vértice " + destino.getRotulo());
        }
    }
}
