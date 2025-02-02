package util;

import model.Grafo;
import model.Aresta;
import model.Vertice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DotFileWriter {

    public static void salvarGrafo(Grafo grafo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("grafos/" + grafo.getNome() + ".dot"))) {
            writer.write((grafo.isDirecionado() ? "digraph " : "graph ") + grafo.getNome() + " {\n");

            Set<String> verticesEscritos = new HashSet<>();

            for (Vertice vertice : grafo.getVertices()) {
                String linhaVertice = "    " + vertice.getRotulo();
                if (vertice.getCorVertice() != null) {
                    linhaVertice += " [color=\"" + vertice.getCorVertice().name().toLowerCase() + "\"]";
                }
                linhaVertice += ";\n";

                if (!verticesEscritos.contains(linhaVertice)) {
                    writer.write(linhaVertice);
                    verticesEscritos.add(linhaVertice);
                }
            }

            for (Aresta aresta : grafo.getArestas()) {
                String linhaAresta = "    " + aresta.getOrigem().getRotulo() + (grafo.isDirecionado() ? " -> " : " -- ") + aresta.getDestino().getRotulo();
                boolean hasAttributes = aresta.getPeso() != 1.0 || aresta.getCorAresta() != null;
                if (hasAttributes) {
                    linhaAresta += " [";
                    if (aresta.getPeso() != 1.0) {
                        linhaAresta += "label=\"" + aresta.getPeso() + "\"";
                    }
                    if (aresta.getCorAresta() != null) {
                        if (aresta.getPeso() != 1.0) {
                            linhaAresta += " ";
                        }
                        linhaAresta += "color=\"" + aresta.getCorAresta().name().toLowerCase() + "\"";
                    }
                    linhaAresta += "]";
                }
                linhaAresta += ";\n";

                writer.write(linhaAresta);
            }

            writer.write("}\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o grafo: " + e.getMessage());
        }
    }
}
