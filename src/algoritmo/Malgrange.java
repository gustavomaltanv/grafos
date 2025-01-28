package algoritmo;

import model.Aresta;
import model.Grafo;
import model.Vertice;

import java.util.*;

public class Malgrange {
    private Stack<Vertice> pilha;
    private Set<Vertice> visitados;
    private Map<Vertice, Set<Vertice>> componentes;

    public Malgrange() {
        this.pilha = new Stack<>();
        this.visitados = new HashSet<>();
        this.componentes = new HashMap<>();
    }

    public void executar(Grafo grafo) {
        // Primeira passagem: DFS no grafo original
        for (Vertice vertice : grafo.getVertices()) {
            if (!visitados.contains(vertice)) {
                primeiraPassagem(grafo, vertice);
            }
        }

        // Transposição do grafo
        Grafo grafoTransposto = transporGrafo(grafo);

        visitados.clear();

        // Segunda passagem: DFS no grafo transposto
        while (!pilha.isEmpty()) {
            Vertice vertice = pilha.pop();
            if (!visitados.contains(vertice)) {
                Set<Vertice> componente = new HashSet<>();
                segundaPassagem(grafoTransposto, vertice, componente);
                componentes.put(vertice, componente);
            }
        }

        // Imprimir componentes fortemente conexas
        System.out.println("Componentes Fortemente Conexas:");
        for (Map.Entry<Vertice, Set<Vertice>> entry : componentes.entrySet()) {
            System.out.print("Componente: " + entry.getKey().getRotulo() + " -> ");
            for (Vertice v : entry.getValue()) {
                System.out.print(v.getRotulo() + " ");
            }
            System.out.println();
        }
    }

    private void primeiraPassagem(Grafo grafo, Vertice vertice) {
        visitados.add(vertice);
        for (Aresta aresta : vertice.getArestas()) {
            Vertice destino = aresta.getDestino();
            if (!visitados.contains(destino)) {
                primeiraPassagem(grafo, destino);
            }
        }
        pilha.push(vertice);
    }

    private void segundaPassagem(Grafo grafo, Vertice vertice, Set<Vertice> componente) {
        visitados.add(vertice);
        componente.add(vertice);
        for (Aresta aresta : vertice.getArestas()) {
            Vertice destino = aresta.getDestino();
            if (!visitados.contains(destino)) {
                segundaPassagem(grafo, destino, componente);
            }
        }
    }

    private Grafo transporGrafo(Grafo grafo) {
        Grafo grafoTransposto = new Grafo(grafo.getNome() + "_Transposto", grafo.isDirecionado());
        Map<String, Vertice> mapaVertices = new HashMap<>();

        for (Vertice vertice : grafo.getVertices()) {
            Vertice novoVertice = new Vertice(vertice.getRotulo());
            grafoTransposto.addVertice(novoVertice);
            mapaVertices.put(vertice.getRotulo(), novoVertice);
        }

        for (Aresta aresta : grafo.getArestas()) {
            Vertice origem = mapaVertices.get(aresta.getOrigem().getRotulo());
            Vertice destino = mapaVertices.get(aresta.getDestino().getRotulo());
            grafoTransposto.addAresta(new Aresta(destino, origem, aresta.getPeso()));
        }

        return grafoTransposto;
    }
}
