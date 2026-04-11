package cadastropets.modelo;

import cadastropets.util.ExcecaoNegocio;
import cadastropets.util.TextoUtil;

public enum SexoPet {
    MACHO("Macho"),
    FEMEA("Femea");

    private final String rotulo;

    SexoPet(String rotulo) {
        this.rotulo = rotulo;
    }

    public String rotulo() {
        return rotulo;
    }

    public static SexoPet doTexto(String valor) {
        String normalizado = TextoUtil.normalizarParaBusca(valor);
        return switch (normalizado) {
            case "macho" -> MACHO;
            case "femea", "femea " -> FEMEA;
            default -> throw new ExcecaoNegocio("Sexo invalido. Digite Macho ou Femea.");
        };
    }
}
