package algoritmo;

import model.Grafo;
import model.Vertice;
import model.Aresta;

import java.util.*;

public class Prim {

    public void executar(Grafo grafo, Vertice origem) {
        Set<Vertice> verticesVisitados = new HashSet<>();
        List<Aresta> arvoreGeradora = new ArrayList<>();
        PriorityQueue<Aresta> arestasDisponiveis = new PriorityQueue<>(Comparator.comparing(Aresta::getPeso));

        verticesVisitados.add(origem);
        grafo.getArestas().stream()
                .filter(aresta -> aresta.getOrigem().equals(origem))
                .forEach(arestasDisponiveis::add);

        while (!arestasDisponiveis.isEmpty()) {
            Aresta aresta = arestasDisponiveis.poll();

            if (!verticesVisitados.contains(aresta.getDestino())) {
                arvoreGeradora.add(aresta);
                Vertice novoVertice = aresta.getDestino();
                verticesVisitados.add(novoVertice);

                grafo.getArestas().stream()
                        .filter(a -> a.getOrigem().equals(novoVertice) && !verticesVisitados.contains(a.getDestino()))
                        .forEach(arestasDisponiveis::add);
            }
        }

        System.out.println("Árvore Geradora Mínima (Prim) a partir do vértice " + origem.getRotulo() + ":");
        for (Aresta aresta : arvoreGeradora) {
            System.out.println(aresta.getOrigem().getRotulo() + " - " + aresta.getDestino().getRotulo() + " : " + aresta.getPeso());
        }
    }
}
