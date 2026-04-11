package cadastropets.modelo;

import cadastropets.util.ValidacaoUtil;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private final String nomeCompleto;
    private final TipoPet tipo;
    private final SexoPet sexo;
    private final Endereco endereco;
    private final double idade;
    private final double peso;
    private final String raca;
    private final LocalDateTime dataCadastro;
    private final List<String> respostasExtras;
    private Path arquivoOrigem;

    public Pet(
            String nomeCompleto,
            TipoPet tipo,
            SexoPet sexo,
            Endereco endereco,
            double idade,
            double peso,
            String raca,
            LocalDateTime dataCadastro,
            List<String> respostasExtras
    ) {
        this.nomeCompleto = nomeCompleto;
        this.tipo = tipo;
        this.sexo = sexo;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
        this.dataCadastro = dataCadastro;
        this.respostasExtras = new ArrayList<>(respostasExtras);
    }

    public String nomeCompleto() {
        return nomeCompleto;
    }

    public TipoPet tipo() {
        return tipo;
    }

    public SexoPet sexo() {
        return sexo;
    }

    public Endereco endereco() {
        return endereco;
    }

    public double idade() {
        return idade;
    }

    public double peso() {
        return peso;
    }

    public String raca() {
        return raca;
    }

    public LocalDateTime dataCadastro() {
        return dataCadastro;
    }

    public List<String> respostasExtras() {
        return new ArrayList<>(respostasExtras);
    }

    public Path arquivoOrigem() {
        return arquivoOrigem;
    }

    public void definirArquivoOrigem(Path arquivoOrigem) {
        this.arquivoOrigem = arquivoOrigem;
    }

    public List<String> paraLinhasDoArquivo() {
        List<String> linhas = new ArrayList<>();
        linhas.add(nomeCompleto);
        linhas.add(tipo.rotulo());
        linhas.add(sexo.rotulo());
        linhas.add(endereco.formatado());
        linhas.add(ValidacaoUtil.formatarDecimal(idade) + " anos");
        linhas.add(ValidacaoUtil.formatarDecimal(peso) + "kg");
        linhas.add(raca);
        linhas.addAll(respostasExtras);
        return linhas;
    }

    public String paraExibicao() {
        return nomeCompleto + " - " + tipo.rotulo() + " - " + sexo.rotulo() + " - " + endereco.formatado()
                + " - " + ValidacaoUtil.formatarDecimal(idade) + " anos"
                + " - " + ValidacaoUtil.formatarDecimal(peso) + "kg"
                + " - " + raca;
    }
}
