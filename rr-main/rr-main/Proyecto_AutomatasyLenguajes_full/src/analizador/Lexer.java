package analizador;

import modelo.Reporte;
import java.io.*;
import java.util.regex.*;

public class Lexer {

    private BufferedReader reader;
    private Reporte reporte;

    public Lexer(Reader r, Reporte rep) {
        this.reader = new BufferedReader(r);
        this.reporte = rep;
    }

    public void analizar() throws IOException {
        String linea;
        int numLinea = 0;

        Pattern patronFuncion = Pattern.compile("function\\s+[a-zA-Z_]\\w*\\s*\\(([^)]*)\\)\\s*\\{");
        Pattern patronAsignacion = Pattern.compile("\\s*var\\s+[a-zA-Z_]\\w*\\s*=\\s*[^;]+;");
        Pattern patronIf = Pattern.compile("if\\s*\\(([^)]*)\\)\\s*\\{");
        Pattern patronDo = Pattern.compile("do\\s*\\{");
        Pattern patronWhile = Pattern.compile("while\\s*\\(([^)]*)\\)");

        while ((linea = reader.readLine()) != null) {
            numLinea++;
            linea = linea.trim();

            if (linea.isEmpty()) continue;

            try {
                // ===== FUNCIONES =====
                if (patronFuncion.matcher(linea).find()) {
                    reporte.addFuncionValida();
                    String parametros = linea.substring(linea.indexOf('(') + 1, linea.indexOf(')'));
                    if (!parametros.isEmpty()) {
                        String[] parts = parametros.split(",");
                        for (String p : parts) {
                            if (p.trim().matches("[a-zA-Z_]\\w*")) {
                                reporte.addParametroValido();
                            } else {
                                reporte.addParametroInvalido();
                            }
                        }
                    }
                } else if (linea.startsWith("function")) {
                    reporte.addFuncionInvalida();
                }

                // ===== ASIGNACIONES =====
                if (patronAsignacion.matcher(linea).find()) {
                    reporte.addAsignacionValida();
                } else if (linea.contains("=") && !linea.contains("==")) {
                    reporte.addAsignacionInvalida();
                }

                // ===== IF =====
                if (patronIf.matcher(linea).find()) {
                    reporte.addIfValido();
                    Matcher m = patronIf.matcher(linea);
                    if (m.find()) {
                        String cond = m.group(1);
                        if (cond.matches(".*[<>!=]=?.*")) {
                            reporte.addCondicionValida();
                        } else {
                            reporte.addCondicionInvalida();
                        }
                    }
                } else if (linea.startsWith("if")) {
                    reporte.addIfInvalido();
                }

                // ===== DO =====
                if (patronDo.matcher(linea).find()) {
                    reporte.addDoValido();
                } else if (linea.startsWith("do")) {
                    reporte.addDoInvalido();
                }

                // ===== WHILE =====
                if (patronWhile.matcher(linea).find()) {
                    Matcher m = patronWhile.matcher(linea);
                    if (m.find()) {
                        String cond = m.group(1);
                        if (cond.matches(".*[<>!=]=?.*")) {
                            reporte.addCondicionValida();
                        } else {
                            reporte.addCondicionInvalida();
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error en línea " + numLinea + ": " + e.getMessage());
                reporte.addErrorSintactico();
            }
        }
    }
}
