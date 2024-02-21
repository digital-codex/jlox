package com.craftinginterpreters.lox;

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return "(assign " + expr.name.lexeme() + " "
                + expr.value.accept(this) + ")";
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return "(" + expr.operator.lexeme() + " "
                + expr.left.accept(this) + " "
                + expr.right.accept(this) + ")";
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        String callee = expr.callee.accept(this);
        Expr[] arguments = new Expr[expr.arguments.size()];
        for (int i = 0; i < expr.arguments.size(); ++i) {
            arguments[i] = expr.arguments.get(i);
        }
        return this.parenthesize(callee, arguments);
    }

    @Override
    public String visitGetExpr(Expr.Get expr) {
        return this.parenthesize(expr.name.lexeme(), expr.object);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return this.parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        return (expr.value == null) ? "nil" : expr.value.toString();
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return this.parenthesize(expr.operator.lexeme(), expr.left, expr.left);
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return this.parenthesize(expr.name.lexeme(), expr.object, expr.value);
    }

    @Override
    public String visitSuperExpr(Expr.Super expr) {
        return expr.keyword.lexeme() + "." + expr.method.lexeme();
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return expr.keyword.lexeme();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return "(" + expr.operator.lexeme() + " " + expr.right.accept(this) + ")";
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme();
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr :exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    public static void main(String[] args) {
/*
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(
                                Token.TokenType.MINUS, "-", null, 1
                        ),
                        new Expr.Literal(123)
                ),
                new Token(Token.TokenType.STAR, "*", null, 1),
                new Expr.Grouping(new Expr.Literal(45.67))
        );

        System.out.println(new AstPrinter().print(expression));
*/
    }
}