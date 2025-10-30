import analizador.Lexer;
import modelo.Reporte;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String input = "entrada.txt";
        if (args.length > 0) input = args[0];

        try (FileReader fr = new FileReader(input)) {
            Reporte reporte = new Reporte();
            Lexer lexer = new Lexer(fr, reporte);
            lexer.analizar();

            System.out.println("\n--- REPORTE FINAL ---");
            reporte.mostrar();
            reporte.escribirArchivo("reporte.txt");
            System.out.println("\n✅ Reporte generado correctamente: reporte.txt");

        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo: " + e.getMessage());
        }
    }
}
