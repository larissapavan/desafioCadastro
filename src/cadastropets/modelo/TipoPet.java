package cadastropets.modelo;

import cadastropets.util.ExcecaoNegocio;
import cadastropets.util.TextoUtil;

public enum TipoPet {
    CACHORRO("Cachorro"),
    GATO("Gato");

    private final String rotulo;

    TipoPet(String rotulo) {
        this.rotulo = rotulo;
    }

    public String rotulo() {
        return rotulo;
    }

    public static TipoPet doTexto(String valor) {
        String normalizado = TextoUtil.normalizarParaBusca(valor);
        return switch (normalizado) {
            case "cachorro" -> CACHORRO;
            case "gato" -> GATO;
            default -> throw new ExcecaoNegocio("Tipo invalido. Digite Cachorro ou Gato.");
        };
    }
}
