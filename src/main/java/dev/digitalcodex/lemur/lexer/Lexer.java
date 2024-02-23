package dev.digitalcodex.lemur.lexer;

import dev.digitalcodex.lemur.CharacterStream;
import dev.digitalcodex.lemur.scanner.Scanner;

public class Lexer {
    private final Scanner scanner;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Lexer(CharacterStream source) {
        this.scanner = new Scanner(source);
    }
}