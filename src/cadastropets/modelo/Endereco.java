package cadastropets.modelo;

public record Endereco(String rua, String numero, String cidade) {
    public String formatado() {
        return rua + ", " + numero + ", " + cidade;
    }
}
