package com.craftinginterpreters.lox;

import java.util.List;

abstract class Stmt {
    interface Visitor {
        void visitBlockStmt(Block stmt);
        void visitClassStmt(Class stmt);
        void visitExpressionStmt(Expression stmt);
        void visitFunctionStmt(Function stmt);
        void visitIfStmt(If stmt);
        void visitPrintStmt(Print stmt);
        void visitReturnStmt(Return stmt);
        void visitVarStmt(Var stmt);
        void visitWhileStmt(While stmt);
    }
    static class Block extends Stmt {
        Block(List<Stmt> statements) {
            this.statements = statements;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitBlockStmt(this);
        }

        final List<Stmt> statements;
    }
    static class Class extends Stmt {
        Class(Token name, Expr.Variable superclass, List<Stmt.Function> methods) {
            this.name = name;
            this.superclass = superclass;
            this.methods = methods;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitClassStmt(this);
        }

        final Token name;
        final Expr.Variable superclass;
        final List<Stmt.Function> methods;
    }
    static class Expression extends Stmt {
        Expression(Expr expression) {
            this.expression = expression;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitExpressionStmt(this);
        }

        final Expr expression;
    }
    static class Function extends Stmt {
        Function(Token name, List<Token> params, List<Stmt> body) {
            this.name = name;
            this.params = params;
            this.body = body;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitFunctionStmt(this);
        }

        final Token name;
        final List<Token> params;
        final List<Stmt> body;
    }
    static class If extends Stmt {
        If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitIfStmt(this);
        }

        final Expr condition;
        final Stmt thenBranch;
        final Stmt elseBranch;
    }
    static class Print extends Stmt {
        Print(Expr expression) {
            this.expression = expression;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitPrintStmt(this);
        }

        final Expr expression;
    }
    static class Return extends Stmt {
        Return(Token keyword, Expr value) {
            this.keyword = keyword;
            this.value = value;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitReturnStmt(this);
        }

        final Token keyword;
        final Expr value;
    }
    static class Var extends Stmt {
        Var(Token name, Expr initializer) {
            this.name = name;
            this.initializer = initializer;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitVarStmt(this);
        }

        final Token name;
        final Expr initializer;
    }
    static class While extends Stmt {
        While(Expr condition, Stmt body) {
            this.condition = condition;
            this.body = body;
        }

        @Override
        void accept(Visitor visitor) {
            visitor.visitWhileStmt(this);
        }

        final Expr condition;
        final Stmt body;
    }

    abstract void accept(Visitor visitor);
}
