package algoritmo;

import model.Grafo;
import model.Vertice;
import model.Aresta;

import java.util.*;

public class Kruskal {

    public void executar(Grafo grafo) {
        List<Aresta> arvoreGeradora = new ArrayList<>();
        List<Aresta> arestasOrdenadas = new ArrayList<>(grafo.getArestas());
        arestasOrdenadas.sort(Comparator.comparing(Aresta::getPeso));

        Map<Vertice, Vertice> parent = new HashMap<>();
        for (Vertice vertice : grafo.getVertices()) {
            parent.put(vertice, vertice);
        }

        for (Aresta aresta : arestasOrdenadas) {
            Vertice origem = find(parent, aresta.getOrigem());
            Vertice destino = find(parent, aresta.getDestino());

            if (!origem.equals(destino)) {
                arvoreGeradora.add(aresta);
                parent.put(origem, destino);
            }
        }

        System.out.println("Árvore Geradora Mínima (Kruskal):");
        for (Aresta aresta : arvoreGeradora) {
            System.out.println(aresta.getOrigem().getRotulo() + " - " + aresta.getDestino().getRotulo() + " : " + aresta.getPeso());
        }
    }

    private Vertice find(Map<Vertice, Vertice> parent, Vertice vertice) {
        if (!parent.get(vertice).equals(vertice)) {
            parent.put(vertice, find(parent, parent.get(vertice)));
        }
        return parent.get(vertice);
    }
}
