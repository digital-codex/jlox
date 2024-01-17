package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Parser {
    private static class ParseError extends RuntimeException {}

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

/* Parsing staements
    Expr parse() {
        try {
            return this.expression();
        } catch (ParseError error) {
            return null;
        }
    }
*/
    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!this.isAtEnd()) {
/* Parsing variables 8.2.2
            statements.add(this.statement());
*/
            statements.add(this.declaration());
        }

        return statements;
    }

    private Expr expression() {
/* Assignment syntax
        return this.equality();
*/
        return this.assignment();
    }

    private Stmt declaration() {
        try {
            if (this.match(TokenType.VAR)) return this.varDeclaration();

            return this.statement();
        } catch (ParseError error) {
            this.synchronize();
            return null;
        }
    }

    private Stmt statement() {
        if (this.match(TokenType.FOR)) return this.forStatement();
        if (this.match(TokenType.IF)) return this.ifStatement();
        if (this.match(TokenType.PRINT)) return this.printStatement();
        if (this.match(TokenType.WHILE)) return this.whileStatement();
        if (this.match(TokenType.LEFT_BRACE))
            return new Stmt.Block(this.block());

        return this.expressionStatement();
    }

    private Stmt forStatement() {
        this.consume(TokenType.LEFT_PAREN, "Expect '(' after 'for'.");

        Stmt initializer;
        if (this.match(TokenType.SEMICOLON)) {
            initializer = null;
        } else if (this.match(TokenType.VAR)) {
            initializer = this.varDeclaration();
        } else {
            initializer = this.expressionStatement();
        }

        Expr condition = null;
        if (!this.check(TokenType.SEMICOLON)) {
            condition = this.expression();
        }
        this.consume(
                TokenType.SEMICOLON, "Expect ';' after loop condition."
        );

        Expr increment = null;
        if (!this.check(TokenType.RIGHT_PAREN)) {
            increment = this.expression();
        }
        this.consume(
                TokenType.RIGHT_PAREN, "Expect ')' after for clauses."
        );
        Stmt body = this.statement();

        if (increment != null) {
            body = new Stmt.Block(
                    Arrays.asList(body, new Stmt.Expression(increment))
            );
        }

        if (condition == null) condition = new Expr.Literal(true);
        body = new Stmt.While(condition, body);

        if (initializer != null) {
            body = new Stmt.Block(Arrays.asList(initializer, body));
        }

        return body;
    }

    private Stmt ifStatement() {
        this.consume(TokenType.LEFT_PAREN, "Expect '(' after 'if'.");
        Expr condition = this.expression();
        this.consume(
                TokenType.RIGHT_PAREN, "Expect ')' after if condition."
        );

        Stmt thenBranch = this.statement();
        Stmt elseBranch = null;
        if (this.match(TokenType.ELSE)) {
            elseBranch = this.statement();
        }

        return new Stmt.If(condition, thenBranch, elseBranch);
    }

    private Stmt printStatement() {
        Expr value = this.expression();
        this.consume(TokenType.SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(value);
    }

    private Stmt varDeclaration() {
        Token name = this.consume(
                TokenType.IDENTIFIER, "Expect variable name."
        );

        Expr initializer = null;
        if (this.match(TokenType.EQUAL)) {
            initializer = this.expression();
        }

        this.consume(
                TokenType.SEMICOLON,
                "Expect ';' after variable declaration."
        );
        return new Stmt.Var(name, initializer);
    }

    private Stmt whileStatement() {
        this.consume(
                TokenType.LEFT_PAREN, "Expect '(' after 'while'."
        );
        Expr condition = this.expression();
        this.consume(
                TokenType.RIGHT_PAREN, "Expect ')' after condition."
        );
        Stmt body = this.statement();

        return new Stmt.While(condition, body);
    }

    private Stmt expressionStatement() {
        Expr expr = this.expression();
        this.consume(
                TokenType.SEMICOLON, "Expect ';' after expression."
        );
        return new Stmt.Expression(expr);
    }

    private List<Stmt> block() {
        List<Stmt> statements = new ArrayList<>();

        while (!this.check(TokenType.RIGHT_BRACE) && !this.isAtEnd()) {
            statements.add(this.declaration());
        }

        this.consume(TokenType.RIGHT_BRACE, "Expect '}' after block.");
        return statements;
    }

    private Expr assignment() {
/* Logical Operators 9.3
        Expr expr = this.equality();
*/
        Expr expr = this.or();

        if (this.match(TokenType.EQUAL)) {
            Token equals = this.previous();
            Expr value = this.assignment();

            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, value);
            }

            this.error(equals, "Invalid assignment target.");
        }

        return expr;
    }

    private Expr or() {
        Expr expr = this.and();

        while (this.match(TokenType.OR)) {
            Token operator = this.previous();
            Expr right = this.and();
            expr = new Expr.Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr and() {
        Expr expr = this.equality();

        while (this.match(TokenType.AND)) {
            Token operator = this.previous();
            Expr right = this.equality();
            expr = new Expr.Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr equality() {
        Expr expr = this.comparison();

        while (this.match(TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL)) {
            Token operator = this.previous();
            Expr right = this.comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = this.term();

        while (
                this.match(
                        TokenType.GREATER, TokenType.GREATER_EQUAL,
                        TokenType.LESS, TokenType.LESS_EQUAL
                )
        ) {
            Token operator = this.previous();
            Expr right = this.term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr term() {
        Expr expr = this.factor();

        while (this.match(TokenType.MINUS, TokenType.PLUS)) {
            Token operator = this.previous();
            Expr right = this.factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = this.unary();

        while (this.match(TokenType.SLASH, TokenType.STAR)) {
            Token operator = this.previous();
            Expr right = this.unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (this.match(TokenType.BANG, TokenType.MINUS)) {
            Token operator = this.previous();
            Expr right = this.unary();
            return new Expr.Unary(operator, right);
        }

        return this.primary();
    }

    private Expr primary() {
        if (this.match(TokenType.FALSE)) return new Expr.Literal(false);
        if (this.match(TokenType.TRUE)) return new Expr.Literal(true);
        if (this.match(TokenType.NIL)) return new Expr.Literal(null);

        if (this.match(TokenType.NUMBER, TokenType.STRING)) {
            return new Expr.Literal(this.previous().literal);
        }

        if (this.match(TokenType.IDENTIFIER)) {
            return new Expr.Variable(this.previous());
        }

        if (match(TokenType.LEFT_PAREN)) {
            Expr expr = this.expression();
            this.consume(
                    TokenType.RIGHT_PAREN,
                    "Expect ')' after expression."
            );
            return new Expr.Grouping(expr);
        }

        throw this.error(this.peek(), "Expect expression.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (this.check(type)) {
                this.advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String message) {
        if (this.check(type)) return this.advance();

        throw this.error(this.peek(), message);
    }

    private boolean check(TokenType type) {
        if (this.isAtEnd()) return false;
        return this.peek().type == type;
    }

    private Token advance() {
        if (!this.isAtEnd()) this.current++;
        return this.previous();
    }

    private boolean isAtEnd() {
        return this.peek().type == TokenType.EOF;
    }

    private Token peek() {
        return this.tokens.get(this.current);
    }

    private Token previous() {
        return this.tokens.get(this.current - 1);
    }

    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        this.advance();

        while (!this.isAtEnd()) {
            if (this.previous().type == TokenType.SEMICOLON) return;

            switch (this.peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            this.advance();
        }
    }
}
