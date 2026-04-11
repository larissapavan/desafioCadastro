package cadastropets.util;

import java.text.Normalizer;
import java.util.Locale;

public final class TextoUtil {
    public static final String NAO_INFORMADO = "NAO INFORMADO";

    private TextoUtil() {
    }

    public static String normalizarParaBusca(String valor) {
        if (valor == null) {
            return "";
        }
        String normalizado = Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalizado.toLowerCase(Locale.ROOT).trim();
    }

    public static String limparEspacos(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim().replaceAll("\\s+", " ");
    }

    public static String preencherSeVazio(String valor) {
        String textoLimpo = limparEspacos(valor);
        return textoLimpo.isBlank() ? NAO_INFORMADO : textoLimpo;
    }
}
