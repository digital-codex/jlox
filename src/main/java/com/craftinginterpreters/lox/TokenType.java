package com.craftinginterpreters.lox;

enum TokenType {
    // Single-character tokens.
    LPAREN, RPAREN, LBRACE, RBRACE,
    COMMA, DOT, MINUS, PLUS, SEMI, SLASH, STAR,

    // One or two character tokens.
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    MORE, MORE_EQUAL,
    LESS, LESS_EQUAL,

    // Literals.
    IDENT, STRING, NUMBER,

    // Keywords.
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}