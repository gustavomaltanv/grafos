import algoritmo.*;
import model.Grafo;
import model.Vertice;
import model.Aresta;
import util.DotFileWriter;
import util.DotFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Main {
    private static List<Grafo> grafos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1 - Criar novo grafo");
            System.out.println("2 - Listar grafos");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarNovoGrafo(scanner);
                    break;
                case 2:
                    listarGrafos(scanner);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void criarNovoGrafo(Scanner scanner) {
        System.out.print("Digite o nome do grafo: ");
        String nome = scanner.nextLine();
        System.out.print("O grafo é direcionado? (true/false): ");
        boolean direcionado = scanner.nextBoolean();
        scanner.nextLine();

        Grafo grafo = new Grafo(nome, direcionado);
        grafos.add(grafo);
        System.out.println("Grafo criado com sucesso!");

        boolean grafoSalvo = false;

        while (true) {
            System.out.println("Menu de Edição:");
            System.out.println("1 - Adicionar vértice");
            System.out.println("2 - Remover vértice");
            System.out.println("3 - Adicionar aresta");
            System.out.println("4 - Remover aresta");
            System.out.println("5 - Salvar grafo");
            System.out.println("6 - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o rótulo do vértice: ");
                    String rotuloVertice = scanner.nextLine();
                    Vertice vertice = new Vertice(rotuloVertice);
                    grafo.addVertice(vertice);
                    System.out.println("Vértice adicionado com sucesso!");
                    break;
                case 2:
                    System.out.print("Digite o rótulo do vértice a remover: ");
                    String rotuloRemoverVertice = scanner.nextLine();
                    grafo.getVertices().removeIf(v -> v.getRotulo().equals(rotuloRemoverVertice));
                    System.out.println("Vértice removido com sucesso!");
                    break;
                case 3:
                    System.out.print("Digite o rótulo do vértice de origem: ");
                    String origem = scanner.nextLine();
                    System.out.print("Digite o rótulo do vértice de destino: ");
                    String destino = scanner.nextLine();
                    System.out.print("Digite o peso da aresta: ");
                    double peso = scanner.nextDouble();
                    scanner.nextLine();

                    Vertice verticeOrigem = grafo.getVertices().stream()
                            .filter(v -> v.getRotulo().equals(origem))
                            .findFirst()
                            .orElse(null);

                    Vertice verticeDestino = grafo.getVertices().stream()
                            .filter(v -> v.getRotulo().equals(destino))
                            .findFirst()
                            .orElse(null);

                    if (verticeOrigem != null && verticeDestino != null) {
                        Aresta aresta = new Aresta(verticeOrigem, verticeDestino, peso);
                        grafo.addAresta(aresta);
                        System.out.println("Aresta adicionada com sucesso!");
                    } else {
                        System.out.println("Vértice de origem ou destino não encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Digite o rótulo do vértice de origem da aresta a remover: ");
                    String origemRemover = scanner.nextLine();
                    System.out.print("Digite o rótulo do vértice de destino da aresta a remover: ");
                    String destinoRemover = scanner.nextLine();

                    Aresta arestaRemover = grafo.encontrarAresta(origemRemover, destinoRemover);
                    if (arestaRemover != null) {
                        grafo.removerAresta(arestaRemover);
                        System.out.println("Aresta removida com sucesso!");
                    }
                    else {
                        System.out.println("Aresta não encontrada.");
                    }

                    break;
                case 5:
                    DotFileWriter.salvarGrafo(grafo);
                    System.out.println("Grafo salvo com sucesso!");
                    grafoSalvo = true;
                    break;
                case 6:
                    if (!grafoSalvo) {
                        grafos.remove(grafo);
                        System.out.println("Grafo não salvo. Descartando alterações.");
                    }
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void listarGrafos(Scanner scanner) {
        File folder = new File("grafos");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".dot"));

        if (listOfFiles != null && listOfFiles.length > 0) {
            System.out.println("Selecione seu grafo:");
            for (int i = 0; i < listOfFiles.length; i++) {
                System.out.println((i + 1) + " - " + listOfFiles[i].getName());
            }
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao > 0 && opcao <= listOfFiles.length) {
                String nomeArquivo = listOfFiles[opcao - 1].getName();
                System.out.println("Você selecionou o grafo: " + nomeArquivo);
                Grafo grafo = DotFileReader.carregarGrafo(nomeArquivo);
                if (grafo != null) {
                    System.out.println("Grafo carregado com sucesso!");
                    menuGrafo(scanner, grafo);
                } else {
                    System.out.println("Erro ao carregar o grafo.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        } else {
            System.out.println("Nenhum grafo encontrado.");
        }
    }

    private static void menuGrafo(Scanner scanner, Grafo grafo) {
        while (true) {
            System.out.println("Menu do Grafo:");
            System.out.println("1 - Visualizar grafo");
            System.out.println("2 - Editar grafo");
            System.out.println("3 - Algoritmo: Dijkstra");
            System.out.println("4 - Algoritmo: Prim");
            System.out.println("5 - Algoritmo: Kruskal");
            System.out.println("6 - Algoritmo: Malgrange");
            System.out.println("7 - Menu: Coloracao");
            System.out.println("8 - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    visualizarGrafo(grafo);
                    break;
                case 2:
                    editarGrafo(scanner, grafo);
                    break;
                case 3:
                    executarDijkstra(scanner, grafo);
                    break;
                case 4:
                    executarPrim(scanner, grafo);
                    break;
                case 5:
                    executarKruskal(scanner, grafo);
                    break;
                case 6:
                    executarMalgrange(scanner, grafo);
                    break;
                case 7:
                    menuColoracao(scanner, grafo);
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void visualizarGrafo(Grafo grafo) {
        System.out.println("Grafo: " + grafo.getNome() + " (ordem: " + grafo.getOrdem() + ")");
        System.out.println("Vertices:");
        for (Vertice vertice : grafo.getVertices()) {
            System.out.println(" " + vertice.getRotulo() + " (grau: " + grafo.getGrau(vertice) + ")");
        }
        System.out.println("Arestas:");
        for (Aresta aresta : grafo.getArestas()) {
            String arrow = grafo.isDirecionado() ? " -> " : " -- ";
            System.out.println(" " + aresta.getOrigem().getRotulo() + arrow + aresta.getDestino().getRotulo());
        }
    }

    private static void editarGrafo(Scanner scanner, Grafo grafo) {
        boolean grafoSalvo = false;

        while (true) {
            System.out.println("Menu de Edição:");
            System.out.println("1 - Adicionar vértice");
            System.out.println("2 - Remover vértice");
            System.out.println("3 - Adicionar aresta");
            System.out.println("4 - Remover aresta");
            System.out.println("5 - Salvar grafo");
            System.out.println("6 - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o rótulo do vértice: ");
                    String rotuloVertice = scanner.nextLine();
                    Vertice vertice = new Vertice(rotuloVertice);
                    grafo.addVertice(vertice);
                    System.out.println("Vértice adicionado com sucesso!");
                    break;
                case 2:
                    System.out.print("Digite o rótulo do vértice a remover: ");
                    String rotuloRemoverVertice = scanner.nextLine();
                    grafo.getVertices().removeIf(v -> v.getRotulo().equals(rotuloRemoverVertice));
                    System.out.println("Vértice removido com sucesso!");
                    break;
                case 3:
                    System.out.print("Digite o rótulo do vértice de origem: ");
                    String origem = scanner.nextLine();
                    System.out.print("Digite o rótulo do vértice de destino: ");
                    String destino = scanner.nextLine();
                    System.out.print("Digite o peso da aresta: ");
                    double peso = scanner.nextDouble();
                    scanner.nextLine();

                    Vertice verticeOrigem = grafo.getVertices().stream()
                            .filter(v -> v.getRotulo().equals(origem))
                            .findFirst()
                            .orElse(null);

                    Vertice verticeDestino = grafo.getVertices().stream()
                            .filter(v -> v.getRotulo().equals(destino))
                            .findFirst()
                            .orElse(null);

                    if (verticeOrigem != null && verticeDestino != null) {
                        Aresta aresta = new Aresta(verticeOrigem, verticeDestino, peso);
                        grafo.addAresta(aresta);
                        System.out.println("Aresta adicionada com sucesso!");
                    } else {
                        System.out.println("Vértice de origem ou destino não encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Digite o rótulo do vértice de origem da aresta a remover: ");
                    String origemRemover = scanner.nextLine();
                    System.out.print("Digite o rótulo do vértice de destino da aresta a remover: ");
                    String destinoRemover = scanner.nextLine();

                    grafo.getArestas().removeIf(a -> a.getOrigem().getRotulo().equals(origemRemover) &&
                            a.getDestino().getRotulo().equals(destinoRemover));
                    System.out.println("Aresta removida com sucesso!");
                    break;
                case 5:
                    DotFileWriter.salvarGrafo(grafo);
                    System.out.println("Grafo salvo com sucesso!");
                    grafoSalvo = true;
                    break;
                case 6:
                    if (!grafoSalvo) {
                        System.out.println("Alterações não salvas.");
                    }
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void executarDijkstra(Scanner scanner, Grafo grafo) {
        System.out.print("Digite o rótulo do vértice de origem: ");
        String origem = scanner.nextLine();

        Vertice verticeOrigem = grafo.getVertices().stream()
                .filter(v -> v.getRotulo().equals(origem))
                .findFirst()
                .orElse(null);

        if (verticeOrigem == null) {
            System.out.println("Vértice de origem não encontrado.");
            return;
        }

        System.out.print("Digite o rótulo do vértice de destino: ");
        String destino = scanner.nextLine();

        Vertice verticeDestino = grafo.getVertices().stream()
                .filter(v -> v.getRotulo().equals(destino))
                .findFirst()
                .orElse(null);

        if (verticeDestino == null) {
            System.out.println("Vértice de destino não encontrado.");
            return;
        }

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.executar(grafo, verticeOrigem, verticeDestino);
    }


    private static void executarPrim(Scanner scanner, Grafo grafo) {
        System.out.print("Digite o rótulo do vértice de origem: ");
        String origem = scanner.nextLine();

        Vertice verticeOrigem = grafo.getVertices().stream()
                .filter(v -> v.getRotulo().equals(origem))
                .findFirst()
                .orElse(null);

        if (verticeOrigem == null) {
            System.out.println("Vértice de origem não encontrado.");
            return;
        }

        Prim prim = new Prim();
        prim.executar(grafo, verticeOrigem);
    }

    private static void executarKruskal(Scanner scanner, Grafo grafo) {
        Kruskal kruskal = new Kruskal();
        kruskal.executar(grafo);
    }

    private static void executarMalgrange(Scanner scanner, Grafo grafo) {
        Malgrange malgrange = new Malgrange();
        malgrange.executar(grafo);
    }

    private static void menuColoracao(Scanner scanner, Grafo grafo) {
        boolean grafoSalvo = false;

        while (true) {
            System.out.println("Menu de Coloração:");
            System.out.println("1 - Coloração de Vértices");
            System.out.println("2 - Coloração de Arestas");
            System.out.println("3 - Salvar grafo");
            System.out.println("4 - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    executarColoracaoVertices(scanner, grafo);
                    break;
                case 2:
                    executarColoracaoArestas(scanner, grafo);
                    break;
                case 3:
                    DotFileWriter.salvarGrafo(grafo);
                    System.out.println("Grafo salvo com sucesso!");
                    grafoSalvo = true;
                    break;
                case 4:
                    if (!grafoSalvo) {
                        System.out.println("Alterações não salvas.");
                    }
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void executarColoracaoVertices(Scanner scanner, Grafo grafo) {
        Coloracao coloracao = new Coloracao();
        coloracao.coloracaoVertices(grafo);
        System.out.println("Vertices coloridos com sucesso!");
    }

    private static void executarColoracaoArestas(Scanner scanner, Grafo grafo) {
        Coloracao coloracao = new Coloracao();
        coloracao.coloracaoArestas(grafo);
        System.out.println("Arestas coloridas com sucesso!");
    }

}
