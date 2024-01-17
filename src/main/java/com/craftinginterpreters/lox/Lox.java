package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            Lox.runFile(args[0]);
        } else {
            Lox.runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        Lox.run(new String(bytes, Charset.defaultCharset()));

        // Indicate an error in the exit code.
        if (Lox.hadError) System.exit(65);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            Lox.run(line);
            Lox.hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
/* Wiring up the Parser 6.4

        // For now, just print the tokens.
        for (Token token : tokens) {
            System.out.println(token);
        }
*/
        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        // Stop if there was a syntax error.
        if (Lox.hadError) return;

        System.out.println(new AstPrinter().print(expression));
    }

    static void error(int line, String message) {
        Lox.report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message
        );
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            Lox.report(token.line, " at end", message);
        } else {
            Lox.report(
                    token.line, " at '" + token.lexeme + "'", message
            );
        }
    }
}