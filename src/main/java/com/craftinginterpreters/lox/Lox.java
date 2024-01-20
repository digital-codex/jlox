package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lox {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;

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
        Path file = Paths.get(path);
        if (Files.exists(file)) {
            Lox.runFile(file);
        } else {
            String name = path.split("\\.")[0].trim().toUpperCase();
            if (Character.isDigit(name.charAt(0))) {
                name = "$" + name;
            }
            List<ScriptType> potential = new ArrayList<>();
            for (ScriptType type : ScriptType.values()) {
                String[] split = type.name().split("_");
                String join = Arrays.stream(split)
                        .filter(x -> !x.equals(split[split.length - 1]))
                        .collect(Collectors.joining("_"));
                if (name.compareToIgnoreCase(join) == 0) {
                    potential.add(type);
                }
            }

            if (potential.size() > 1) {
                System.out.println(
                        "You have multiple files matching: " + path
                );
                System.out.println(
                        "please chose the file you would like ot execute"
                );
                for (ScriptType type : potential) {
                    String[] indexes = type.name().split("_");
                    System.out.println(
                            indexes[indexes.length - 1] + " : "
                                    + ScriptType.lookup(type)
                    );
                }
                InputStreamReader input = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(input);

                int index;
                for (;;) {
                    String in = reader.readLine();
                    index = Integer.parseInt(in);
                    if (index > 0 && index <= potential.size())
                        break;

                    System.out.println(in + " is not a valid index");
                    for (ScriptType type : potential) {
                        String[] indexes = type.name().split("_");
                        System.out.println(
                                indexes[indexes.length - 1] + " : "
                                        + ScriptType.lookup(type)
                        );
                    }
                }

                Lox.runFile(ScriptType.lookup(potential.get(index - 1)));
            } else if (potential.size() == 1) {
                Lox.runFile(ScriptType.lookup(potential.get(0)));
            } else {
                System.out.println(
                        path + " is not a valid script or a cached script name."
                );
                System.exit(65);
            }
        }
    }

    private static void runFile(Path path) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        Lox.run(new String(bytes, Charset.defaultCharset()));

        // Indicate an error in the exit code.
        if (Lox.hadError) System.exit(65);
        if (Lox.hadRuntimeError) System.exit(70);
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
        Parser parser = new Parser(tokens);
/* Statements 8.1
        Expr expression = parser.parse();
*/
        List<Stmt> statements = parser.parse();

        // Stop if there was a syntax error.
        if (Lox.hadError) return;

/* Statements 8.1
        Lox.interpreter.interpret(expression);
*/
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        // Stop if there was a resolution error.
        if (Lox.hadError) return;

        Lox.interpreter.interpret(statements);
    }

    static void error(int line, String message) {
        Lox.report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.out.println(
                "[line " + line + "] Error" + where + ": " + message
        );
        Lox.hadError = true;
    }

    static void error(Token token, String message) {
        if (token.type() == TokenType.EOF) {
            Lox.report(token.line(), " at end", message);
        } else {
            Lox.report(
                    token.line(), " at '" + token.lexeme() + "'", message
            );
        }
    }

    static void runtimeError(RuntimeError error) {
        System.out.println(
                error.getMessage() + "\n[line " + error.token.line() + "]"
        );
        Lox.hadRuntimeError = true;
    }
}