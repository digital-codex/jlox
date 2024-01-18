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

/* Statements 8.1
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
/* Global Variables 8.2
            statements.add(this.statement());
*/
            statements.add(this.declaration());
        }

        return statements;
    }

    private Expr expression() {
/* Assignment 8.4
        return this.equality();
*/
        return this.assignment();
    }

    private Stmt declaration() {
        try {
            if (this.match(TokenType.CLASS)) return this.classDeclaration();
            if (this.match(TokenType.FUN))
                return this.function("function");
            if (this.match(TokenType.VAR)) return this.varDeclaration();

            return this.statement();
        } catch (ParseError error) {
            this.synchronize();
            return null;
        }
    }

    private Stmt classDeclaration() {
        Token name = this.consume(
                TokenType.IDENTIFIER, "Expect class name."
        );

        Expr.Variable superclass = null;
        if (this.match(TokenType.LESS)) {
            this.consume(
                    TokenType.IDENTIFIER, "Expect superclass name."
            );
            superclass = new Expr.Variable(this.previous());
        }

        this.consume(
                TokenType.LEFT_BRACE, "Expect '{' before class body."
        );

        List<Stmt.Function> methods = new ArrayList<>();
        while (!this.check(TokenType.RIGHT_BRACE) && !this.isAtEnd()) {
            methods.add(this.function("method"));
        }

        this.consume(
                TokenType.RIGHT_BRACE, "Expect '}' after class body."
        );

        return new Stmt.Class(name, superclass, methods);
    }

    private Stmt statement() {
        if (this.match(TokenType.FOR)) return this.forStatement();
        if (this.match(TokenType.IF)) return this.ifStatement();
        if (this.match(TokenType.PRINT)) return this.printStatement();
        if (this.match(TokenType.RETURN)) return this.returnStatement();
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

    private Stmt returnStatement() {
        Token keyword = this.previous();
        Expr value = null;
        if (!this.check(TokenType.SEMICOLON)) {
            value = this.expression();
        }

        this.consume(
                TokenType.SEMICOLON, "Expect ';' after return value."
        );
        return new Stmt.Return(keyword, value);
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

    private Stmt.Function function(String kind) {
        Token name = this.consume(
                TokenType.IDENTIFIER, "Expect " + kind + " name."
        );
        this.consume(
                TokenType.LEFT_PAREN, "Expect '(' after " + kind
                        + " name."
        );
        List<Token> parameters = new ArrayList<>();
        if (!this.check(TokenType.RIGHT_PAREN)) {
            do {
                if (parameters.size() >= 255) {
                    this.error(
                            this.peek(),
                            "Can't have more than 255 parameters."
                    );
                }

                parameters.add(
                        this.consume(
                                TokenType.IDENTIFIER,
                                "Expect parameter name."
                        )
                );
            } while (this.match(TokenType.COMMA));
        }
        this.consume(
                TokenType.RIGHT_PAREN, "Expect ')' after parameters."
        );

        this.consume(
                TokenType.LEFT_BRACE, "Expect '{' before " + kind
                        + " body."
        );
        List<Stmt> body = this.block();
        return new Stmt.Function(name, parameters, body);
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
            } else if (expr instanceof Expr.Get get) {
                return new Expr.Set(get.object, get.name, value);
            }

            this.error(equals, "Invalid assignment target.");
        }

        return expr;
    }

    private Expr or() {
        return this.infix(this::and, Expr.Logical::new, TokenType.OR);
    }

    private Expr and() {
        return this.infix(this::equality, Expr.Logical::new, TokenType.AND);
    }

    private Expr equality() {
        return this.infix(
                this::comparison, Expr.Binary::new,
                TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL
        );
    }

    private Expr comparison() {
        return this.infix(
                this::term, Expr.Binary::new,
                TokenType.GREATER, TokenType.GREATER_EQUAL,
                TokenType.LESS, TokenType.LESS_EQUAL
        );
    }

    private Expr term() {
        return this.infix(
                this::factor, Expr.Binary::new, TokenType.MINUS, TokenType.PLUS
        );
    }

    private Expr factor() {
        return this.infix(
                this::unary, Expr.Binary::new, TokenType.SLASH, TokenType.STAR
        );
    }

    @FunctionalInterface
    interface Production {
        Expr generate();
    }

    @FunctionalInterface
    interface Infix {
        Expr build(Expr left, Token operator, Expr right);
    }

    private Expr infix(Production next, Infix infix, TokenType... matches) {
        Expr expr = next.generate();

        while (this.match(matches)) {
            Token operator = this.previous();
            Expr right = next.generate();
            expr = infix.build(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (this.match(TokenType.BANG, TokenType.MINUS)) {
            Token operator = this.previous();
            Expr right = this.unary();
            return new Expr.Unary(operator, right);
        }

/* Function Calls 10.1
        return this.primary();
*/
        return this.call();
    }

    private Expr finishCall(Expr callee) {
        List<Expr> arguments = new ArrayList<>();
        if (!this.check(TokenType.RIGHT_PAREN)) {
            do {
                if (arguments.size() >= 255) {
                    this.error(
                            this.peek(),
                            "Can't have more than 255 arguments."
                    );
                }
                arguments.add(this.expression());
            } while (this.match(TokenType.COMMA));
        }

        Token paren = this.consume(
                TokenType.RIGHT_PAREN, "Expect ')' after arguments."
        );

        return new Expr.Call(callee, paren, arguments);
    }

    private Expr call() {
        Expr expr = this.primary();

        while (true) {
            if (this.match(TokenType.LEFT_PAREN)) {
                expr = this.finishCall(expr);
            } else if (this.match(TokenType.DOT)) {
                Token name = this.consume(
                        TokenType.IDENTIFIER,
                        "Expect property name after '.'."
                );
                expr = new Expr.Get(expr, name);
            } else {
                break;
            }
        }

        return expr;
    }

    private Expr primary() {
        if (this.match(TokenType.FALSE)) return new Expr.Literal(false);
        if (this.match(TokenType.TRUE)) return new Expr.Literal(true);
        if (this.match(TokenType.NIL)) return new Expr.Literal(null);

        if (this.match(TokenType.NUMBER, TokenType.STRING)) {
            return new Expr.Literal(this.previous().literal);
        }

        if (this.match(TokenType.SUPER)) {
            Token keyword = this.previous();
            this.consume(TokenType.DOT, "Expect '.' after 'super'.");
            Token method = this.consume(
                    TokenType.IDENTIFIER,
                    "Expect superclass method name."
            );
            return new Expr.Super(keyword, method);
        }

        if (this.match(TokenType.THIS)) return new Expr.This(this.previous());

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

        throw this.panic(this.peek(), "Expect expression.");
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

    // TODO: return value of consume is not used in multiple places
    private Token consume(TokenType type, String message) {
        if (this.check(type)) return this.advance();

        throw this.panic(this.peek(), message);
    }

    private boolean check(TokenType type) {
        if (this.isAtEnd()) return false;
        return this.peek().type == type;
    }

    // TODO: return value of advance is not used in multiple places
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

    private void error(Token token, String message) {
        Lox.error(token, message);
    }

    private ParseError panic(Token token, String message) {
        this.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        this.advance();

        while (!this.isAtEnd()) {
            if (this.previous().type == TokenType.SEMICOLON) return;

            switch (this.peek().type) {
                case CLASS, FUN, VAR, FOR, IF, WHILE, PRINT, RETURN -> {
                    return;
                }
            }

            this.advance();
        }
    }
}
