package cadastropets.util;

import java.util.Locale;

public final class ValidacaoUtil {
    private static final String LETTERS_AND_SPACES = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$";

    private ValidacaoUtil() {
    }

    public static String validarNomeCompleto(String valor) {
        String textoLimpo = TextoUtil.limparEspacos(valor);
        if (textoLimpo.isBlank()) {
            throw new ExcecaoNegocio("O nome completo do pet é obrigatório.");
        }
        if (!textoLimpo.matches(LETTERS_AND_SPACES)) {
            throw new ExcecaoNegocio("O nome deve conter apenas letras e espaços.");
        }
        String[] partes = textoLimpo.split(" ");
        if (partes.length < 2) {
            throw new ExcecaoNegocio("Informe nome e sobrenome do pet.");
        }
        return textoLimpo;
    }

    public static String validarRacaOuPadrao(String valor) {
        String textoLimpo = TextoUtil.limparEspacos(valor);
        if (textoLimpo.isBlank()) {
            return TextoUtil.NAO_INFORMADO;
        }
        if (!textoLimpo.matches(LETTERS_AND_SPACES)) {
            throw new ExcecaoNegocio("A raça deve conter apenas letras e espaços.");
        }
        return textoLimpo;
    }

    public static String validarRuaOuPadrao(String valor) {
        return TextoUtil.preencherSeVazio(valor);
    }

    public static String validarCidadeOuPadrao(String valor) {
        return TextoUtil.preencherSeVazio(valor);
    }

    public static String validarNumeroOuPadrao(String valor) {
        String textoLimpo = TextoUtil.limparEspacos(valor);
        if (textoLimpo.isBlank()) {
            return TextoUtil.NAO_INFORMADO;
        }
        if (!textoLimpo.matches("^\\d+[A-Za-z]?$")) {
            throw new ExcecaoNegocio("O número do endereço deve conter apenas números.");
        }
        return textoLimpo;
    }

    public static double interpretarPeso(String valor) {
        double peso = interpretarDecimalComPadrao(valor, true);
        if (peso != -1 && (peso < 0.5 || peso > 60)) {
            throw new ExcecaoNegocio("O peso deve estar entre 0.5kg e 60kg.");
        }
        return peso;
    }

    public static double interpretarIdade(String valor) {
        double idade = interpretarDecimalComPadrao(valor, true);
        if (idade != -1 && idade > 20) {
            throw new ExcecaoNegocio("A idade não pode ser maior que 20 anos.");
        }
        if (idade != -1 && idade >= 1 && idade < 12) {
            idade = idade / 12.0;
        }
        return idade;
    }

    public static double interpretarDecimalBusca(String valor) {
        return interpretarDecimalComPadrao(valor, false);
    }

    private static double interpretarDecimalComPadrao(String valor, boolean aceitarVazio) {
        String textoLimpo = TextoUtil.limparEspacos(valor).replace(",", ".");
        if (textoLimpo.isBlank()) {
            if (aceitarVazio) {
                return -1;
            }
            throw new ExcecaoNegocio("Informe um número válido.");
        }
        if (!textoLimpo.matches("^\\d+(\\.\\d+)?$")) {
            throw new ExcecaoNegocio("Digite apenas números, usando ponto ou vírgula.");
        }
        return Double.parseDouble(textoLimpo);
    }

    public static String formatarDecimal(double valor) {
        if (valor < 0) {
            return TextoUtil.NAO_INFORMADO;
        }
        String formatado = String.format(Locale.US, "%.2f", valor);
        formatado = formatado.replaceAll("0+$", "").replaceAll("\\.$", "");
        return formatado;
    }
}
