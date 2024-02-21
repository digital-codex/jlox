package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

import static com.craftinginterpreters.lox.Token.TokenType;

public class Lexer {
    @FunctionalInterface
    public interface ErrorHandler {
        void handle(String msg);
    }

    private enum ErrorType {
        UNEXPECTED_CHARACTER("unexpected character"),
        UNTERMINATED_STRING("unterminated string");

        private final String value;

        ErrorType(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("fn", TokenType.FN);
        keywords.put("if", TokenType.IF);
        keywords.put("var", TokenType.VAR);
        keywords.put("else", TokenType.ELSE);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("return", TokenType.RETURN);

        keywords.put("and", TokenType.AND);
        keywords.put("class", TokenType.CLASS);
        keywords.put("for", TokenType.FOR);
        keywords.put("nil", TokenType.NIL);
        keywords.put("or", TokenType.OR);
        keywords.put("print", TokenType.PRINT);
        keywords.put("super", TokenType.SUPER);
        keywords.put("this", TokenType.THIS);
        keywords.put("while", TokenType.WHILE);
    }

    private final String source;

    private int start = 0;
    private int current = 0;

    private int line = 1;
    private int lineIdx = 0;

    private final ErrorHandler handler;
    private int errorCnt = 0;

    public Lexer(String source, ErrorHandler handler) {
        this.source = source;
        this.handler = handler;
    }

    public Token next() {
        while (this.current < this.source.length()) {
            this.start = this.current;

            char c = this.peek(0);
            switch (c) {
                case '=' -> {
                    return this.emit(
                            this.match('=') ? TokenType.EQUAL_EQUAL
                                    : TokenType.EQUAL
                    );
                }
                case '!' -> {
                    return this.emit(
                            this.match('=') ? TokenType.BANG_EQUAL
                                    : TokenType.BANG
                    );
                }
                case '<' -> {
                    return this.emit(
                            this.match('=') ? TokenType.LESS_EQUAL
                                    : TokenType.LESS
                    );
                }
                case '>' -> {
                    return this.emit(
                            this.match('=') ? TokenType.MORE_EQUAL
                                    : TokenType.MORE
                    );
                }
                case '+' -> {
                    return this.emit(TokenType.PLUS);
                }
                case '-' -> {
                    return this.emit(TokenType.MINUS);
                }
                case '*' -> {
                    return this.emit(TokenType.STAR);
                }
                case '/' -> {
                    if (this.match('/')) {
                        // A comment goes until the end of the line.
                        for (char ch = this.peek(0); ch != '\n' && ch != 0; ch = this.peek(0)) {
                            this.advance();
                        }
                    } else {
                        return this.emit(TokenType.SLASH);
                    }
                }
                case ',' -> {
                    return this.emit(TokenType.COMMA);
                }
                case '.' -> {
                    return this.emit(TokenType.DOT);
                }
                case ';' -> {
                    return this.emit(TokenType.SEMICOLON);
                }
                case '(' -> {
                    return this.emit(TokenType.LPAREN);
                }
                case ')' -> {
                    return this.emit(TokenType.RPAREN);
                }
                case '{' -> {
                    return this.emit(TokenType.LBRACE);
                }
                case '}' -> {
                    return this.emit(TokenType.RBRACE);
                }
                case '\t', '\n', '\r', ' ' -> {
                    for (char ch = this.peek(0); ch == '\t' ||  ch == '\n' || ch == '\r' || ch == ' '; ch = this.peek(0)) {
                        if (ch == '\n') {
                            this.lineIdx = this.current; this.line++;
                        }
                        this.advance();
                    }
                }
                case '"' -> {
                    return this.string();
                }
                default -> {
                    if (this.isDigit(c)) {
                        return this.number();
                    } else if (this.isAlpha(c)) {
                        return this.ident();
                    } else {
                        Lox.error(line, "Unexpected character.");
                    }
                }
            }
        }

        return this.emit(TokenType.EOF, "");
    }

    private Token string() {
        // The opening quote.
        this.advance();

        for (char ch = this.peek(0); ch != '"' && ch != '\n' && ch != 0; ch = this.peek(0)) this.advance();

        if (this.peek(0) != '"') {
            return this.error(ErrorType.UNTERMINATED_STRING);
        }

        // The closing quote.
        this.advance();

        // Trim the surrounding quotes.
        return this.emit(TokenType.STRING, this.source.substring(this.start + 1, this.current - 1));
    }

    private Token ident() {
        while (this.isAlphaNumeric(this.peek(0))) this.advance();

        String text = this.source.substring(this.start, this.current);
        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.IDENT;
        return this.emit(type, text);
    }

    private Token number() {
        while (this.isDigit(this.peek(0))) this.advance();

        // Look for a fractional part.
        if (this.peek(0) == '.' && this.isDigit(this.peek(1))) {
            // Consume the "."
            this.advance();


            while (this.isDigit(this.peek(0))) this.advance();
        }

        return this.emit(TokenType.NUMBER, this.source.substring(this.start, this.current));
    }

    private char peek(int n) {
        if (this.current + n < this.source.length())
            return this.source.charAt(this.current + n);
        else
            return '\0';
    }

    private void advance() {
        if (this.current < this.source.length()) this.current++;
    }

    private boolean match(char expected) {
        if (this.peek(1) == expected) {
            this.advance();
            return true;
        }

        return false;
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlphaNumeric(char c) {
        return this.isAlpha(c) || this.isDigit(c);
    }

    private Token emit(TokenType type) {
        this.advance();
        return this.emit(type, type.lexeme());
    }

    private Token emit(TokenType type, String lexeme) {
        return new Token(type, this.current, this.current - this.start, this.line, lexeme);
    }

    private Token error(ErrorType type) {
        if (this.handler != null) {
            StringBuilder msg = new StringBuilder(String.format("Error: %s\n     ", type.value()));
            int start = 0;
            if (this.lineIdx != 0) {
                start = this.lineIdx + 1;
            }
            String line = String.format("%d | %s\n", this.line, this.source.substring(start, this.current));
            msg.append(line);
            int off = line.length();
            msg.append(" ".repeat(off+2));
            msg.append("^--- Here");

            this.handler.handle(msg.toString());
        }
        this.errorCnt++;

        return this.emit(TokenType.ILLEGAL, type.value());
    }
}