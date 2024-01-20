package com.craftinginterpreters.lox;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class LoxTest {
    static class TestInterpreter extends Interpreter {
        List<String> actual = new ArrayList<>();
        List<String> expected;

        TestInterpreter(List<String> expected) {
            super();
            this.expected = expected;
        }

        @Override
        void interpret(List<Stmt> statements) {
            try {
                for (Stmt statement : statements) {
                    this.execute(statement);
                }
            } catch (RuntimeError error) {
                Lox.runtimeError(error);
            }

            this.check();
        }

        @Override
        public void visitPrintStmt(Stmt.Print stmt) {
            Object value = this.evaluate(stmt.expression);
            this.actual.add(this.stringify(value));
        }

        public void check() {
            for (int i = 0; i < this.expected.size(); ++i) {
                Assertions.assertEquals(this.expected.get(i), this.actual.get(i));
            }
        }
    }

    @Nested
    @DisplayName("Assignment Tests")
    class Assignment {
        private static List<Stmt> stmts;

        @BeforeAll
        static void setup() {
            Assignment.stmts = new ArrayList<>();
        }

        @Test
        @DisplayName("Test Associativity")
        void test_associativity() {
            final TestInterpreter interpreter = new TestInterpreter(List.of("c", "c", "c"));

            final Token a = new Token(TokenType.IDENTIFIER, "a", null, 1);
            final Token b = new Token(TokenType.IDENTIFIER, "b", null, 2);
            final Token c = new Token(TokenType.IDENTIFIER, "c", null, 2);

            Assignment.stmts.add(new Stmt.Var(a, new Expr.Literal("a")));
            Assignment.stmts.add(new Stmt.Var(b, new Expr.Literal("b")));
            Assignment.stmts.add(new Stmt.Var(c, new Expr.Literal("c")));
            Assignment.stmts.add(
                    new Stmt.Expression(
                            new Expr.Assign(a, new Expr.Assign(b, new Expr.Variable(c)))
                    )
            );
            Assignment.stmts.add(new Stmt.Print(new Expr.Variable(a)));
            Assignment.stmts.add(new Stmt.Print(new Expr.Variable(b)));
            Assignment.stmts.add(new Stmt.Print(new Expr.Variable(c)));

            interpreter.interpret(Assignment.stmts);
        }
    }
}
