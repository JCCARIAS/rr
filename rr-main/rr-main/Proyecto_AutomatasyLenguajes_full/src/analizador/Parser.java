

package analizador;

import modelo.Reporte;
import java.io.IOException;

public class Parser {
    private final Lexer lexer;
    private Lexer.Token lookahead;
    public Reporte reporte;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.reporte = new Reporte();
    }

    private void next() throws IOException {
        lookahead = lexer.nextToken();
    }

    private void expect(Lexer.TokenType t) throws IOException, ParseException {
        if (lookahead.type == t) {
            next();
        } else {
            throw new ParseException("Se esperaba " + t + " pero vino " + lookahead.type + " en línea " + lookahead.line);
        }
    }

    public void parse() throws IOException {
        next();
        try {
            funcion();
            System.out.println("--- REPORTE DE VALIDACIÓN ---");
            reporte.mostrar();
            System.out.println("Reporte escrito en reporte.txt");
        } catch (ParseException ex) {
            System.err.println("Error sintáctico: " + ex.getMessage());
            reporte.erroresSintacticos++;
            reporte.mostrar();
        }
    }

    private void funcion() throws IOException, ParseException {
        expect(Lexer.TokenType.FUNCION_A);
        expect(Lexer.TokenType.PARAM_A);
        parametros();
        expect(Lexer.TokenType.PARAM_C);
        expect(Lexer.TokenType.CODIGO_A);
        codigo();
        expect(Lexer.TokenType.CODIGO_C);
        expect(Lexer.TokenType.FUNCION_C);
        reporte.funciones++;
    }

    private void parametros() throws IOException, ParseException {
        int valid = 0;
        while (lookahead.type == Lexer.TokenType.ID || lookahead.type == Lexer.TokenType.NUM) {
            valid++;
            next();
            if (lookahead.type == Lexer.TokenType.DELIM) next();
            else break;
        }
        reporte.parametrosValidos = valid;
        reporte.parametrosInvalidos = 0;
    }

    private void codigo() throws IOException, ParseException {
        while (lookahead.type != Lexer.TokenType.CODIGO_C &&
               lookahead.type != Lexer.TokenType.FUNCION_C &&
               lookahead.type != Lexer.TokenType.EOF) {

            switch (lookahead.type) {
                case ID:
                    if (asignacion()) reporte.asignacionesValidas++;
                    else next(); // ignorar texto ajeno
                    break;
                case IF_A:
                    ifBloque();
                    break;
                case DO_A:
                    doBloque();
                    break;
                default:
                    // ignorar cualquier texto que no sea parte de pseudocódigo
                    next();
                    break;
            }
        }
    }

    private boolean asignacion() throws IOException {
        next(); // ID
        if (lookahead.type != Lexer.TokenType.ASIG) return false;
        next();
        if (lookahead.type == Lexer.TokenType.ID || lookahead.type == Lexer.TokenType.NUM) {
            next();
            if (lookahead.type == Lexer.TokenType.DELIM) {
                next();
                return true;
            }
        }
        return false;
    }

    private void ifBloque() throws IOException, ParseException {
        expect(Lexer.TokenType.IF_A);
        expect(Lexer.TokenType.COND_A);
        condicion();
        expect(Lexer.TokenType.COND_C);
        expect(Lexer.TokenType.CODIGO_A);
        codigo();
        expect(Lexer.TokenType.CODIGO_C);
        expect(Lexer.TokenType.IF_C);
        reporte.ifValidos++;
    }

    private void doBloque() throws IOException, ParseException {
        expect(Lexer.TokenType.DO_A);
        expect(Lexer.TokenType.CODIGO_A);
        codigo();
        expect(Lexer.TokenType.CODIGO_C);

        // ignorar texto no relevante hasta la condición
        while (lookahead.type != Lexer.TokenType.COND_A &&
               lookahead.type != Lexer.TokenType.DO_C &&
               lookahead.type != Lexer.TokenType.EOF) {
            next();
        }

        expect(Lexer.TokenType.COND_A);
        condicion();
        expect(Lexer.TokenType.COND_C);
        expect(Lexer.TokenType.DO_C);
        reporte.doValidos++;
    }

    private void condicion() throws IOException {
        if (lookahead.type == Lexer.TokenType.ID || lookahead.type == Lexer.TokenType.NUM) {
            next();
            if (lookahead.type == Lexer.TokenType.OP_REL) {
                next();
                if (lookahead.type == Lexer.TokenType.ID || lookahead.type == Lexer.TokenType.NUM) {
                    next();
                    reporte.condicionesValidas++;
                    if (lookahead.type == Lexer.TokenType.OP_LOG) {
                        next();
                        condicion();
                    }
                }
            }
        }
    }

    public static class ParseException extends Exception {
        public ParseException(String msg) {
            super(msg);
        }
    }
}
