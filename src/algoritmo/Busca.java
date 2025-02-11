package algoritmo;

import model.Aresta;
import model.Grafo;
import model.Vertice;

import java.util.*;

public class Busca {

    public void executarBFS(Grafo grafo, Vertice inicio) {
        Set<Vertice> visitados = new HashSet<>();
        Queue<Vertice> fila = new LinkedList<>();
        fila.add(inicio);
        visitados.add(inicio);

        while (!fila.isEmpty()) {
            Vertice vertice = fila.poll();
            System.out.print(vertice.getRotulo() + " -> ");

            for (Aresta aresta : vertice.getArestas()) {
                Vertice vizinho = aresta.getDestino();
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }
        System.out.println("fim");
    }

    public void executarDFS(Grafo grafo, Vertice inicio) {
        Set<Vertice> visitados = new HashSet<>();
        Stack<Vertice> pilha = new Stack<>();
        pilha.push(inicio);
        visitados.add(inicio);

        while (!pilha.isEmpty()) {
            Vertice vertice = pilha.pop();
            System.out.print(vertice.getRotulo() + " -> ");

            for (Aresta aresta : vertice.getArestas()) {
                Vertice vizinho = aresta.getDestino();
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    pilha.push(vizinho);
                }
            }
        }
        System.out.println("fim");
    }
}
