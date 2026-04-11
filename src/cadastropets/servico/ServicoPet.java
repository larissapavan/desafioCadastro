package cadastropets.servico;

import cadastropets.modelo.Endereco;
import cadastropets.modelo.Pet;
import cadastropets.modelo.SexoPet;
import cadastropets.modelo.TipoPet;
import cadastropets.util.ExcecaoNegocio;
import cadastropets.util.TextoUtil;
import cadastropets.util.ValidacaoUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicoPet {
    private final ServicoFormulario servicoFormulario;
    private final RepositorioPet repositorio;

    public ServicoPet(ServicoFormulario servicoFormulario, RepositorioPet repositorio) {
        this.servicoFormulario = servicoFormulario;
        this.repositorio = repositorio;
    }

    public void cadastrarPet(Scanner scanner) {
        List<String> perguntas = servicoFormulario.carregarPerguntas();
        perguntas.forEach(System.out::println);

        String nomeCompleto = perguntar(scanner, perguntas.get(0) + " ");
        String tipoInformado = perguntar(scanner, perguntas.get(1) + " ");
        String sexoInformado = perguntar(scanner, perguntas.get(2) + " ");

        System.out.println(perguntas.get(3));
        String numeroCasa = perguntar(scanner, "Numero da casa: ");
        String cidade = perguntar(scanner, "Cidade: ");
        String rua = perguntar(scanner, "Rua: ");

        String idadeInformada = perguntar(scanner, perguntas.get(4) + " ");
        String pesoInformado = perguntar(scanner, perguntas.get(5) + " ");
        String racaInformada = perguntar(scanner, perguntas.get(6) + " ");

        List<String> respostasExtras = new ArrayList<>();
        for (int indice = 7; indice < perguntas.size(); indice++) {
            respostasExtras.add(perguntas.get(indice) + " - " + TextoUtil.preencherSeVazio(perguntar(scanner, perguntas.get(indice) + " ")));
        }

        Pet pet = new Pet(
                ValidacaoUtil.validarNomeCompleto(nomeCompleto),
                TipoPet.doTexto(tipoInformado),
                SexoPet.doTexto(sexoInformado),
                new Endereco(
                        ValidacaoUtil.validarRuaOuPadrao(rua),
                        ValidacaoUtil.validarNumeroOuPadrao(numeroCasa),
                        ValidacaoUtil.validarCidadeOuPadrao(cidade)
                ),
                ValidacaoUtil.interpretarIdade(idadeInformada),
                ValidacaoUtil.interpretarPeso(pesoInformado),
                ValidacaoUtil.validarRacaOuPadrao(racaInformada),
                LocalDateTime.now(),
                respostasExtras
        );

        repositorio.salvar(pet);
        System.out.println("Pet cadastrado com sucesso em: " + pet.arquivoOrigem().getFileName());
    }

    public void listarTodosOsPets() {
        List<Pet> pets = repositorio.buscarTodos();
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }
        exibirResultados(pets);
    }

    public void listarPetsPorCriterio(Scanner scanner) {
        List<Pet> resultados = buscarPets(scanner);
        if (resultados.isEmpty()) {
            System.out.println("Nenhum pet encontrado para os criterios informados.");
            return;
        }
        exibirResultados(resultados);
    }

    public void alterarPet(Scanner scanner) {
        List<Pet> resultados = buscarPets(scanner);
        Pet petSelecionado = escolherPet(scanner, resultados, "alterar");
        if (petSelecionado == null) {
            return;
        }

        String novoNomeCompleto = perguntar(scanner, "Novo nome completo [" + petSelecionado.nomeCompleto() + "]: ");
        String novoNumeroCasa = perguntar(scanner, "Novo numero da casa [" + petSelecionado.endereco().numero() + "]: ");
        String novaCidade = perguntar(scanner, "Nova cidade [" + petSelecionado.endereco().cidade() + "]: ");
        String novaRua = perguntar(scanner, "Nova rua [" + petSelecionado.endereco().rua() + "]: ");
        String novaIdade = perguntar(scanner, "Nova idade aproximada [" + valorNumericoExibivel(petSelecionado.idade()) + "]: ");
        String novoPeso = perguntar(scanner, "Novo peso aproximado [" + valorNumericoExibivel(petSelecionado.peso()) + "]: ");
        String novaRaca = perguntar(scanner, "Nova raca [" + petSelecionado.raca() + "]: ");

        Pet petAtualizado = new Pet(
                novoNomeCompleto.isBlank() ? petSelecionado.nomeCompleto() : ValidacaoUtil.validarNomeCompleto(novoNomeCompleto),
                petSelecionado.tipo(),
                petSelecionado.sexo(),
                new Endereco(
                        novaRua.isBlank() ? petSelecionado.endereco().rua() : ValidacaoUtil.validarRuaOuPadrao(novaRua),
                        novoNumeroCasa.isBlank() ? petSelecionado.endereco().numero() : ValidacaoUtil.validarNumeroOuPadrao(novoNumeroCasa),
                        novaCidade.isBlank() ? petSelecionado.endereco().cidade() : ValidacaoUtil.validarCidadeOuPadrao(novaCidade)
                ),
                novaIdade.isBlank() ? petSelecionado.idade() : ValidacaoUtil.interpretarIdade(novaIdade),
                novoPeso.isBlank() ? petSelecionado.peso() : ValidacaoUtil.interpretarPeso(novoPeso),
                novaRaca.isBlank() ? petSelecionado.raca() : ValidacaoUtil.validarRacaOuPadrao(novaRaca),
                petSelecionado.dataCadastro(),
                petSelecionado.respostasExtras()
        );

        repositorio.deletar(petSelecionado);
        repositorio.salvar(petAtualizado);
        System.out.println("Pet alterado com sucesso.");
    }

    public void deletarPet(Scanner scanner) {
        List<Pet> resultados = buscarPets(scanner);
        Pet petSelecionado = escolherPet(scanner, resultados, "deletar");
        if (petSelecionado == null) {
            return;
        }

        String confirmacao = perguntar(scanner, "Confirma a exclusao? Digite SIM ou NAO: ");
        if (TextoUtil.normalizarParaBusca(confirmacao).equals("sim")) {
            repositorio.deletar(petSelecionado);
            System.out.println("Pet deletado com sucesso.");
            return;
        }
        System.out.println("Exclusao cancelada.");
    }

    private List<Pet> buscarPets(Scanner scanner) {
        List<Pet> todosOsPets = repositorio.buscarTodos();
        if (todosOsPets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
            return List.of();
        }

        TipoPet tipoObrigatorio = TipoPet.doTexto(perguntar(scanner, "Informe o tipo do animal (Cachorro/Gato): "));
        List<Pet> filtrados = todosOsPets.stream()
                .filter(pet -> pet.tipo() == tipoObrigatorio)
                .toList();

        int quantidadeCriterios = lerNumeroNoIntervalo(scanner, "Quantos criterios adicionais deseja usar? (1 ou 2): ", 1, 2);
        for (int criterioAtual = 1; criterioAtual <= quantidadeCriterios; criterioAtual++) {
            int opcao = lerNumeroNoIntervalo(scanner, """
                    Escolha o criterio adicional:
                    1. Nome ou sobrenome
                    2. Sexo
                    3. Idade
                    4. Peso
                    5. Raca
                    6. Endereco
                    Opcao: """, 1, 6);
            String valor = perguntar(scanner, "Digite o valor para o criterio " + criterioAtual + ": ");
            filtrados = aplicarCriterio(filtrados, opcao, valor);
        }

        return filtrados;
    }

    private List<Pet> aplicarCriterio(List<Pet> pets, int opcao, String valor) {
        return switch (opcao) {
            case 1 -> pets.stream().filter(pet ->
                    contemTextoNormalizado(pet.nomeCompleto(), valor)).toList();
            case 2 -> {
                SexoPet sexo = SexoPet.doTexto(valor);
                yield pets.stream().filter(pet -> pet.sexo() == sexo).toList();
            }
            case 3 -> {
                double idade = ValidacaoUtil.interpretarDecimalBusca(valor);
                if (idade >= 1 && idade < 12) {
                    idade = idade / 12.0;
                }
                final double idadeFinal = idade;
                yield pets.stream().filter(pet -> Double.compare(pet.idade(), idadeFinal) == 0).toList();
            }
            case 4 -> {
                double peso = ValidacaoUtil.interpretarDecimalBusca(valor);
                final double pesoFinal = peso;
                yield pets.stream().filter(pet -> Double.compare(pet.peso(), pesoFinal) == 0).toList();
            }
            case 5 -> pets.stream().filter(pet ->
                    contemTextoNormalizado(pet.raca(), valor)).toList();
            case 6 -> pets.stream().filter(pet ->
                    contemTextoNormalizado(pet.endereco().formatado(), valor)).toList();
            default -> throw new ExcecaoNegocio("Criterio invalido.");
        };
    }

    private boolean contemTextoNormalizado(String origem, String termo) {
        return TextoUtil.normalizarParaBusca(origem).contains(TextoUtil.normalizarParaBusca(termo));
    }

    private Pet escolherPet(Scanner scanner, List<Pet> resultados, String acao) {
        if (resultados.isEmpty()) {
            System.out.println("Nenhum pet encontrado para " + acao + ".");
            return null;
        }
        exibirResultados(resultados);
        int indiceSelecionado = lerNumeroNoIntervalo(scanner,
                "Digite o numero do pet que deseja " + acao + ": ", 1, resultados.size());
        return resultados.get(indiceSelecionado - 1);
    }

    private int lerNumeroNoIntervalo(Scanner scanner, String mensagem, int minimo, int maximo) {
        while (true) {
            String valorBruto = perguntar(scanner, mensagem);
            if (!valorBruto.matches("^\\d+$")) {
                System.out.println("Digite apenas numeros.");
                continue;
            }
            int opcao = Integer.parseInt(valorBruto);
            if (opcao < minimo || opcao > maximo) {
                System.out.println("Opcao invalida. Tente novamente.");
                continue;
            }
            return opcao;
        }
    }

    private String perguntar(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private void exibirResultados(List<Pet> pets) {
        for (int indice = 0; indice < pets.size(); indice++) {
            System.out.println((indice + 1) + ". " + pets.get(indice).paraExibicao());
        }
    }

    private String valorNumericoExibivel(double valor) {
        return valor < 0 ? TextoUtil.NAO_INFORMADO : ValidacaoUtil.formatarDecimal(valor);
    }
}
