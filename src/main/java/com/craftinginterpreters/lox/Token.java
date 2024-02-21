package com.craftinginterpreters.lox;

public record Token(TokenType type, int start, int length, int line, String lexeme) {
    enum TokenType {
        ILLEGAL("ILLEGAL"),

        // One or two character tokens.
        EQUAL("="), EQUAL_EQUAL("=="),
        BANG("!"), BANG_EQUAL("!="),
        LESS("<"), LESS_EQUAL("<="),
        MORE(">"), MORE_EQUAL(">="),

        // Single-character tokens.
        PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"),

        COMMA(","), DOT("."), SEMICOLON(";"),

        LPAREN("("), RPAREN(")"), LBRACE("{"), RBRACE("}"),

        // Literals.
        STRING("STRING"), NUMBER("NUMBER"), IDENT("IDENT"),

        // Keywords.
        AND("and"), CLASS("class"), ELSE("else"), FALSE("false"), FN("fn"), FOR("for"), IF("if"), NIL("nil"), OR("or"),
        PRINT("print"), RETURN("return"), SUPER("super"), THIS("this"), TRUE("true"), VAR("var"), WHILE("while"),

        EOF("");

        private final String lexeme;

        TokenType(String lexeme) {
            this.lexeme = lexeme;
        }

        public String lexeme() {
            return this.lexeme;
        }
    }

    @Override
    public String toString() {
        return "Token{" +
                "type:" + this.type +
                ", start:" + this.start +
                ", length:" + this.length +
                ", lexeme:" + this.lexeme +
                ", line:" + this.line +
                "}";
    }
}