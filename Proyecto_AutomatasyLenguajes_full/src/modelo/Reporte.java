package modelo;

import java.io.FileWriter;
import java.io.IOException;

public class Reporte {
    private int funciones = 0;
    private int funcionesInvalidas = 0;
    private int parametrosValidos = 0;
    private int parametrosInvalidos = 0;
    private int asignacionesValidas = 0;
    private int asignacionesInvalidas = 0;
    private int ifValidos = 0;
    private int ifInvalidos = 0;
    private int doValidos = 0;
    private int doInvalidos = 0;
    private int condicionesValidas = 0;
    private int condicionesInvalidas = 0;
    private int erroresLexicos = 0;
    private int erroresSintacticos = 0;

    // Métodos incrementales
    public void addFuncionValida() { funciones++; }
    public void addFuncionInvalida() { funcionesInvalidas++; }
    public void addParametroValido() { parametrosValidos++; }
    public void addParametroInvalido() { parametrosInvalidos++; }
    public void addAsignacionValida() { asignacionesValidas++; }
    public void addAsignacionInvalida() { asignacionesInvalidas++; }
    public void addIfValido() { ifValidos++; }
    public void addIfInvalido() { ifInvalidos++; }
    public void addDoValido() { doValidos++; }
    public void addDoInvalido() { doInvalidos++; }
    public void addCondicionValida() { condicionesValidas++; }
    public void addCondicionInvalida() { condicionesInvalidas++; }
    public void addErrorLexico() { erroresLexicos++; }
    public void addErrorSintactico() { erroresSintacticos++; }

    // Mostrar reporte en consola
    public void mostrar() {
        System.out.println("Funciones válidas: " + funciones);
        System.out.println("Funciones inválidas: " + funcionesInvalidas);
        System.out.println("Parámetros válidos: " + parametrosValidos);
        System.out.println("Parámetros inválidos: " + parametrosInvalidos);
        System.out.println("Asignaciones válidas: " + asignacionesValidas);
        System.out.println("Asignaciones inválidas: " + asignacionesInvalidas);
        System.out.println("If válidos: " + ifValidos);
        System.out.println("If inválidos: " + ifInvalidos);
        System.out.println("Do válidos: " + doValidos);
        System.out.println("Do inválidos: " + doInvalidos);
        System.out.println("Condiciones válidas: " + condicionesValidas);
        System.out.println("Condiciones inválidas: " + condicionesInvalidas);
        System.out.println("Errores léxicos: " + erroresLexicos);
        System.out.println("Errores sintácticos: " + erroresSintacticos);
    }

    // Guardar en archivo
    public void escribirArchivo(String path) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("--- REPORTE FINAL ---\n");
            fw.write("Funciones válidas: " + funciones + "\n");
            fw.write("Funciones inválidas: " + funcionesInvalidas + "\n");
            fw.write("Parámetros válidos: " + parametrosValidos + "\n");
            fw.write("Parámetros inválidos: " + parametrosInvalidos + "\n");
            fw.write("Asignaciones válidas: " + asignacionesValidas + "\n");
            fw.write("Asignaciones inválidas: " + asignacionesInvalidas + "\n");
            fw.write("If válidos: " + ifValidos + "\n");
            fw.write("If inválidos: " + ifInvalidos + "\n");
            fw.write("Do válidos: " + doValidos + "\n");
            fw.write("Do inválidos: " + doInvalidos + "\n");
            fw.write("Condiciones válidas: " + condicionesValidas + "\n");
            fw.write("Condiciones inválidas: " + condicionesInvalidas + "\n");
            fw.write("Errores léxicos: " + erroresLexicos + "\n");
            fw.write("Errores sintácticos: " + erroresSintacticos + "\n");
        }
    }
}
