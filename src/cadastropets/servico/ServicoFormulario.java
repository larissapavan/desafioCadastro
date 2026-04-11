package cadastropets.servico;

import cadastropets.util.ExcecaoNegocio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ServicoFormulario {
    private final Path caminhoFormulario;

    public ServicoFormulario(Path caminhoFormulario) {
        this.caminhoFormulario = caminhoFormulario;
    }

    public List<String> carregarPerguntas() {
        try {
            if (!Files.exists(caminhoFormulario)) {
                throw new ExcecaoNegocio("Arquivo formulario.txt nao encontrado.");
            }
            return Files.readAllLines(caminhoFormulario, StandardCharsets.UTF_8).stream()
                    .filter(line -> !line.isBlank())
                    .toList();
        } catch (IOException excecao) {
            throw new ExcecaoNegocio("Nao foi possivel ler o arquivo formulario.txt.");
        }
    }

    public void exibirPerguntas() {
        carregarPerguntas().forEach(System.out::println);
    }
}
