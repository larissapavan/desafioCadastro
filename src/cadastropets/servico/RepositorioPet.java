package cadastropets.servico;

import cadastropets.modelo.Endereco;
import cadastropets.modelo.Pet;
import cadastropets.modelo.SexoPet;
import cadastropets.modelo.TipoPet;
import cadastropets.util.ExcecaoNegocio;
import cadastropets.util.TextoUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositorioPet {
    private static final DateTimeFormatter FORMATO_NOME_ARQUIVO = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
    private final Path diretorioArmazenamento;

    public RepositorioPet(Path diretorioArmazenamento) {
        this.diretorioArmazenamento = diretorioArmazenamento;
    }

    public void salvar(Pet pet) {
        try {
            Files.createDirectories(diretorioArmazenamento);
            Path destino = diretorioArmazenamento.resolve(montarNomeArquivo(pet));
            Files.write(destino, pet.paraLinhasDoArquivo(), StandardCharsets.UTF_8);
            pet.definirArquivoOrigem(destino);
        } catch (IOException excecao) {
            throw new ExcecaoNegocio("Nao foi possivel salvar o pet em arquivo.");
        }
    }

    public void deletar(Pet pet) {
        try {
            if (pet.arquivoOrigem() != null) {
                Files.deleteIfExists(pet.arquivoOrigem());
            }
        } catch (IOException excecao) {
            throw new ExcecaoNegocio("Nao foi possivel deletar o arquivo do pet.");
        }
    }

    public List<Pet> buscarTodos() {
        try {
            Files.createDirectories(diretorioArmazenamento);
            List<Pet> pets = new ArrayList<>();
            try (var arquivos = Files.list(diretorioArmazenamento)) {
                arquivos.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().toUpperCase().endsWith(".TXT"))
                        .sorted(Comparator.comparing(Path::getFileName))
                        .forEach(path -> pets.add(lerArquivo(path)));
            }
            return pets;
        } catch (IOException excecao) {
            throw new ExcecaoNegocio("Nao foi possivel carregar os pets cadastrados.");
        }
    }

    private Pet lerArquivo(Path caminho) {
        try {
            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8).stream()
                    .filter(line -> !line.isBlank())
                    .toList();
            if (linhas.size() < 7) {
                throw new ExcecaoNegocio("Arquivo invalido encontrado em petsCadastrados: " + caminho.getFileName());
            }
            String[] partesEndereco = linhas.get(3).split(",\\s*");
            String rua = partesEndereco.length > 0 ? partesEndereco[0] : TextoUtil.NAO_INFORMADO;
            String numero = partesEndereco.length > 1 ? partesEndereco[1] : TextoUtil.NAO_INFORMADO;
            String cidade = partesEndereco.length > 2 ? partesEndereco[2] : TextoUtil.NAO_INFORMADO;
            Pet pet = new Pet(
                    linhas.get(0),
                    TipoPet.doTexto(linhas.get(1)),
                    SexoPet.doTexto(linhas.get(2)),
                    new Endereco(rua, numero, cidade),
                    extrairNumero(linhas.get(4)),
                    extrairNumero(linhas.get(5)),
                    linhas.get(6),
                    extrairData(caminho.getFileName().toString()),
                    linhas.size() > 7 ? linhas.subList(7, linhas.size()) : List.of()
            );
            pet.definirArquivoOrigem(caminho);
            return pet;
        } catch (IOException excecao) {
            throw new ExcecaoNegocio("Nao foi possivel ler o arquivo do pet: " + caminho.getFileName());
        }
    }

    private double extrairNumero(String linha) {
        String normalizado = linha.replace("anos", "").replace("kg", "").trim().replace(",", ".");
        if (TextoUtil.normalizarParaBusca(normalizado).equals(TextoUtil.normalizarParaBusca(TextoUtil.NAO_INFORMADO))) {
            return -1;
        }
        return Double.parseDouble(normalizado);
    }

    private LocalDateTime extrairData(String nomeArquivo) {
        String prefixo = nomeArquivo.split("-")[0];
        try {
            return LocalDateTime.parse(prefixo, FORMATO_NOME_ARQUIVO);
        } catch (DateTimeParseException excecao) {
            return LocalDateTime.now();
        }
    }

    private String montarNomeArquivo(Pet pet) {
        String nomeNormalizado = TextoUtil.normalizarParaBusca(pet.nomeCompleto())
                .replace(" ", "")
                .toUpperCase();
        return pet.dataCadastro().format(FORMATO_NOME_ARQUIVO) + "-" + nomeNormalizado + ".TXT";
    }
}
