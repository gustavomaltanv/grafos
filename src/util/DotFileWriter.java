package util;

import model.Grafo;
import model.Aresta;
import model.Vertice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DotFileWriter {

    public static void salvarGrafo(Grafo grafo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("grafos/" + grafo.getNome() + ".dot"))) {
            writer.write((grafo.isDirecionado() ? "digraph " : "graph ") + grafo.getNome() + " {\n");

            for (Vertice vertice : grafo.getVertices()) {
                writer.write("    " + vertice.getRotulo() + ";\n");
            }

            for (Aresta aresta : grafo.getArestas()) {
                String arrow = grafo.isDirecionado() ? " -> " : " -- ";
                writer.write("    " + aresta.getOrigem().getRotulo() + arrow + aresta.getDestino().getRotulo() + " [label=\"" + aresta.getPeso() + "\"];\n");
            }

            writer.write("}\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o grafo: " + e.getMessage());
        }
    }
}
