package analizador;
public class TokenType {
    private String tipo;
    private String valor;
    private int linea;
    private int columna;

    public TokenType(String tipo, String valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo() { return tipo; }
    public String getValor() { return valor; }
    public int getLinea() { return linea; }
    public int getColumna() { return columna; }

    @Override
    public String toString() {
        return String.format("[%s → '%s' en línea %d, col %d]", tipo, valor, linea, columna);
    }
}
