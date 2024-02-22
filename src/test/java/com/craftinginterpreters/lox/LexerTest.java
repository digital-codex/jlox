package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LexerTest {
    @Test
    void test_next() {
        record Test(String input, Token[] expected) {}
        Test[] tests = new Test[]{
                new Test("var five = 5;", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "five"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var ten = 10;", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "ten"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("fn add(x, y) { x + y; }", new Token[]{
                        new Token(Token.TokenType.FN, 0, 0, 1, "fn"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "add"),
                        new Token(Token.TokenType.LPAREN, 0, 0, 1, "("),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "x"),
                        new Token(Token.TokenType.COMMA, 0, 0, 1, ","),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "y"),
                        new Token(Token.TokenType.RPAREN, 0, 0, 1, ")"),
                        new Token(Token.TokenType.LBRACE, 0, 0, 1, "{"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "x"),
                        new Token(Token.TokenType.PLUS, 0, 0, 1, "+"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "y"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.RBRACE, 0, 0, 1, "}"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var result = add(five, ten);", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "result"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "add"),
                        new Token(Token.TokenType.LPAREN, 0, 0, 1, "("),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "five"),
                        new Token(Token.TokenType.COMMA, 0, 0, 1, ","),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "ten"),
                        new Token(Token.TokenType.RPAREN, 0, 0, 1, ")"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var zero = 5 - 5 / 5 * 5;", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "zero"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.MINUS, 0, 0, 1, "-"),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.SLASH, 0, 0, 1, "/"),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.STAR, 0, 0, 1, "*"),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var less = 5 < 10; var more = 10 > 5;", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "less"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.LESS, 0, 0, 1, "<"),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "more"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.MORE, 0, 0, 1, ">"),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("if (!(5 < 10)) { return true; } else { return false; }", new Token[]{
                        new Token(Token.TokenType.IF, 0, 0, 1, "if"),
                        new Token(Token.TokenType.LPAREN, 0, 0, 1, "("),
                        new Token(Token.TokenType.BANG, 0, 0, 1, "!"),
                        new Token(Token.TokenType.LPAREN, 0, 0, 1, "("),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.LESS, 0, 0, 1, "<"),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.RPAREN, 0, 0, 1, ")"),
                        new Token(Token.TokenType.RPAREN, 0, 0, 1, ")"),
                        new Token(Token.TokenType.LBRACE, 0, 0, 1, "{"),
                        new Token(Token.TokenType.RETURN, 0, 0, 1, "return"),
                        new Token(Token.TokenType.TRUE, 0, 0, 1, "true"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.RBRACE, 0, 0, 1, "}"),
                        new Token(Token.TokenType.ELSE, 0, 0, 1, "else"),
                        new Token(Token.TokenType.LBRACE, 0, 0, 1, "{"),
                        new Token(Token.TokenType.RETURN, 0, 0, 1, "return"),
                        new Token(Token.TokenType.FALSE, 0, 0, 1, "false"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.RBRACE, 0, 0, 1, "}"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var equal = 10 == 10;", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "equal"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.EQUAL_EQUAL, 0, 0, 1, "=="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var equal = 10 != 5;", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "equal"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "10"),
                        new Token(Token.TokenType.BANG_EQUAL, 0, 0, 1, "!="),
                        new Token(Token.TokenType.NUMBER, 0, 0, 1, "5"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
                new Test("var foobar = \"foobar\"; var foo_bar = \"foo bar\";", new Token[]{
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "foobar"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.STRING, 0, 0, 1, "foobar"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.VAR, 0, 0, 1, "var"),
                        new Token(Token.TokenType.IDENT, 0, 0, 1, "foo_bar"),
                        new Token(Token.TokenType.EQUAL, 0, 0, 1, "="),
                        new Token(Token.TokenType.STRING, 0, 0, 1, "foo bar"),
                        new Token(Token.TokenType.SEMICOLON, 0, 0, 1, ";"),
                        new Token(Token.TokenType.EOF, 0, 0, 1, ""),
                }),
        };

        for (int i = 0; i < tests.length; ++i) {
            Lexer lexer = new Lexer(tests[i].input, Assertions::fail);
            for (Token expected : tests[i].expected) {
                Token actual = lexer.next();
                Assertions.assertEquals(expected.type(), actual.type(), "test[" + i + "] - Type wrong");
                Assertions.assertEquals(expected.lexeme(), actual.lexeme(), "test[" + i + "] - Lexeme wrong");
            }
        }
    }
}