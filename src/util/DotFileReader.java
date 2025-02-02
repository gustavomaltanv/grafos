package util;

import model.Cor;
import model.Grafo;
import model.Aresta;
import model.Vertice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DotFileReader {

    public static Grafo carregarGrafo(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader("grafos/" + nomeArquivo))) {
            String line;
            boolean direcionado = false;
            String nomeGrafo = "";
            Grafo grafo = null;
            Set<String> verticesLidos = new HashSet<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("graph")) {
                    direcionado = false;
                    nomeGrafo = line.split(" ")[1].replace("{", "").trim();
                    grafo = new Grafo(nomeGrafo, direcionado);
                } else if (line.startsWith("digraph")) {
                    direcionado = true;
                    nomeGrafo = line.split(" ")[1].replace("{", "").trim();
                    grafo = new Grafo(nomeGrafo, direcionado);
                } else if (line.contains("--") || line.contains("->")) {
                    String[] parts = line.split(direcionado ? "->" : "--");
                    String origem = parts[0].trim().replace(";", "");
                    String destino = parts[1].split("\\[")[0].trim().replace(";","");
                    double peso = 1.0;
                    Cor corAresta = null;

                    if (line.contains("label")) {
                        peso = Double.parseDouble(line.split("label=\"")[1].split("\"")[0]);
                    }
                    if (line.contains("color")) {
                        corAresta = Cor.valueOf(line.split("color=\"")[1].split("\"")[0].toUpperCase());
                    }

                    final Grafo grafoFinal = grafo;
                    Vertice verticeOrigem = grafo.getVertices().stream()
                            .filter(v -> v.getRotulo().equals(origem))
                            .findFirst()
                            .orElseGet(() -> {
                                Vertice v = new Vertice(origem);
                                grafoFinal.addVertice(v);
                                return v;
                            });

                    Vertice verticeDestino = grafo.getVertices().stream()
                            .filter(v -> v.getRotulo().equals(destino))
                            .findFirst()
                            .orElseGet(() -> {
                                Vertice v = new Vertice(destino);
                                grafoFinal.addVertice(v);
                                return v;
                            });

                    Aresta aresta = new Aresta(verticeOrigem, verticeDestino, peso);
                    aresta.setCorAresta(corAresta);
                    grafo.addAresta(aresta);
                } else if (line.endsWith(";")) {
                    String vertice = line.replace(";", "").trim();
                    if (!verticesLidos.contains(vertice)) {
                        Vertice v = new Vertice(vertice);
                        grafo.addVertice(v);
                        if (line.contains("color")) {
                            v.setCorVertice(Cor.valueOf(line.split("color=\"")[1].split("\"")[0].toUpperCase()));
                        }
                        verticesLidos.add(vertice);
                    }
                }
            }

            return grafo;
        } catch (IOException e) {
            System.err.println("Erro ao carregar o grafo: " + e.getMessage());
        }
        return null;
    }
}
