package cadastropets;

import cadastropets.servico.RepositorioPet;
import cadastropets.servico.ServicoFormulario;
import cadastropets.servico.ServicoPet;
import cadastropets.util.ExcecaoNegocio;

import java.nio.file.Path;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) {
        Path raizProjeto = Path.of("").toAbsolutePath();
        ServicoFormulario servicoFormulario = new ServicoFormulario(raizProjeto.resolve("formulario.txt"));
        RepositorioPet repositorioPet = new RepositorioPet(raizProjeto.resolve("petsCadastrados"));
        ServicoPet servicoPet = new ServicoPet(servicoFormulario, repositorioPet);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    exibirMenu();
                    int opcao = lerOpcaoMenu(scanner);
                    switch (opcao) {
                        case 1 -> servicoPet.cadastrarPet(scanner);
                        case 2 -> servicoPet.alterarPet(scanner);
                        case 3 -> servicoPet.deletarPet(scanner);
                        case 4 -> servicoPet.listarTodosOsPets();
                        case 5 -> servicoPet.listarPetsPorCriterio(scanner);
                        case 6 -> {
                            System.out.println("Sistema encerrado.");
                            return;
                        }
                        default -> System.out.println("Opção inválida.");
                    }
                } catch (ExcecaoNegocio excecao) {
                    System.out.println("Erro: " + excecao.getMessage());
                }
                System.out.println();
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("Menu inicial:");
        System.out.println("1. Cadastrar um novo pet");
        System.out.println("2. Alterar os dados do pet cadastrado");
        System.out.println("3. Deletar um pet cadastrado");
        System.out.println("4. Listar todos os pets cadastrados");
        System.out.println("5. Listar pets por algum criterio (idade, nome, raca)");
        System.out.println("6. Sair");
    }

    private static int lerOpcaoMenu(Scanner scanner) {
        while (true) {
            System.out.print("Escolha uma opção: ");
            String valor = scanner.nextLine().trim();
            if (!valor.matches("^\\d+$")) {
                System.out.println("Digite apenas números.");
                continue;
            }
            int opcao = Integer.parseInt(valor);
            if (opcao <= 0) {
                System.out.println("Não use zero ou números negativos.");
                continue;
            }
            if (opcao > 6) {
                System.out.println("Opção inválida. O menu será exibido novamente.");
                continue;
            }
            return opcao;
        }
    }
}
