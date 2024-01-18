package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor {
    final Environment globals = new Environment();
    private Environment environment = globals;
    private final Map<Expr, Integer> locals = new HashMap<>();

    Interpreter() {
        globals.define("clock", new LoxCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return (double) System.currentTimeMillis() / 1000.0;
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });
    }

/* Statements 8.1
    void interpret(Expr expression) {
        try {
            Object value = this.evaluate(expression);
            System.out.println(this.stringify(value));
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        }
    }
*/
    void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                this.execute(statement);
            }
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        }
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    void resolve(Expr expr, int depth) {
        this.locals.put(expr, depth);
    }

    void executeBlock(List<Stmt> statements, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;

            for (Stmt statement : statements) {
                this.execute(statement);
            }
        } finally {
            this.environment = previous;
        }
    }

    @Override
    public void visitBlockStmt(Stmt.Block stmt) {
        this.executeBlock(stmt.statements, new Environment(this.environment));
    }

    @Override
    public void visitClassStmt(Stmt.Class stmt) {
        Object superclass = null;
        if (stmt.superclass != null) {
            superclass = this.evaluate(stmt.superclass);
            if (!(superclass instanceof LoxClass)) {
                throw new RuntimeError(
                        stmt.superclass.name,
                        "Superclass must be a class."
                );
            }
        }

        this.environment.define(stmt.name.lexeme, null);
        if (stmt.superclass != null) {
            this.environment = new Environment(this.environment);
            this.environment.define("super", superclass);
        }

        Map<String, LoxFunction> methods = new HashMap<>();
        for (Stmt.Function method : stmt.methods) {
            LoxFunction function = new LoxFunction(
                    method, environment, method.name.lexeme.equals("init")
            );
            methods.put(method.name.lexeme, function);
        }

        LoxClass klass = new LoxClass(
                stmt.name.lexeme, (LoxClass) superclass, methods
        );

        if (superclass != null) {
            this.environment = this.environment.enclosing;
        }

        this.environment.assign(stmt.name, klass);
    }

    @Override
    public void visitExpressionStmt(Stmt.Expression stmt) {
        // TODO: return value of evaluate is not used here
        this.evaluate(stmt.expression);
    }

    @Override
    public void visitFunctionStmt(Stmt.Function stmt) {
        LoxFunction function = new LoxFunction(
                stmt, this.environment, false
        );
        this.environment.define(stmt.name.lexeme, function);
    }

    @Override
    public void visitIfStmt(Stmt.If stmt) {
        if (this.isTruthy(this.evaluate(stmt.condition))) {
            this.execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            this.execute(stmt.elseBranch);
        }
    }

    @Override
    public void visitPrintStmt(Stmt.Print stmt) {
        Object value = this.evaluate(stmt.expression);
        System.out.println(this.stringify(value));
    }

    @Override
    public void visitReturnStmt(Stmt.Return stmt) {
        Object value = null;
        if (stmt.value != null) value = this.evaluate(stmt.value);

        throw new Return(value);
    }

    @Override
    public void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if (stmt.initializer != null) {
            value = this.evaluate(stmt.initializer);
        }

        this.environment.define(stmt.name.lexeme, value);
    }

    @Override
    public void visitWhileStmt(Stmt.While stmt) {
        while (this.isTruthy(this.evaluate(stmt.condition))) {
            this.execute(stmt.body);
        }
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = this.evaluate(expr.value);
/* Interpreting Resolved Variables 11.4
        this.environment.assign(expr.name, value);
*/

        Integer distance = this.locals.get(expr);
        if (distance != null) {
            this.environment.assignAt(distance, expr.name, value);
        } else {
            this.globals.assign(expr.name, value);
        }
        return value;
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = this.evaluate(expr.left);
        Object right = this.evaluate(expr.right);

        return switch (expr.operator.type) {
            case BANG_EQUAL -> !this.isEqual(left, right);
            case EQUAL_EQUAL -> this.isEqual(left, right);
            case GREATER -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left > (double) right;
            }
            case GREATER_EQUAL -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left >= (double) right;
            }
            case LESS -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left < (double) right;
            }
            case LESS_EQUAL -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left <= (double) right;
            }
            case MINUS -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left - (double) right;
            }
            case PLUS -> {
                if (left instanceof Double l && right instanceof Double r) {
                    yield l + r;
                }
                if (left instanceof String && right instanceof String r) {
                    yield  left + r;
                }
                throw new RuntimeError(
                        expr.operator,
                        "Operands must be two numbers or two strings."
                );
            }
            case SLASH -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left / (double) right;
            }
            case STAR -> {
                this.checkNumberOperands(expr.operator, left, right);
                yield  (double) left * (double) right;
            }
            // Unreachable.
            default ->  null;
        };
    }

    @Override
    public Object visitCallExpr(Expr.Call expr) {
        Object callee = this.evaluate(expr.callee);

        List<Object> arguments = new ArrayList<>();
        for (Expr argument : expr.arguments) {
            arguments.add(this.evaluate(argument));
        }

        if (!(callee instanceof LoxCallable function)) {
            throw new RuntimeError(
                    expr.paren, "Can only call function and classes."
            );
        }

        if (arguments.size() != function.arity()) {
            throw new RuntimeError(
                    expr.paren,
                    "Expected " + function.arity()
                            + " arguments but got " + arguments.size() + "."
            );
        }
        return function.call(this, arguments);
    }

    @Override
    public Object visitGetExpr(Expr.Get expr) {
        Object object = this.evaluate(expr.object);
        if (object instanceof LoxInstance instance) {
            return instance.get(expr.name);
        }

        throw new RuntimeError(
                expr.name, "Only instances have properties."
        );
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return this.evaluate(expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
        Object left = this.evaluate(expr.left);

        if (expr.operator.type == TokenType.OR) {
            if (this.isTruthy(left)) return left;
        } else {
            if (!this.isTruthy(left)) return left;
        }

        return this.evaluate(expr.right);
    }

    @Override
    public Object visitSetExpr(Expr.Set expr) {
        Object object = this.evaluate(expr.object);

        if (!(object instanceof LoxInstance)) {
            throw new RuntimeError(
                    expr.name, "Only instances have fields."
            );
        }

        Object value = this.evaluate(expr.value);
        ((LoxInstance) object).set(expr.name, value);
        return value;
    }

    @Override
    public Object visitSuperExpr(Expr.Super expr) {
        int distance = this.locals.get(expr);
        LoxClass superclass = (LoxClass) this.environment.getAt(
                distance, "super"
        );

        LoxInstance object = (LoxInstance) this.environment.getAt(
                distance - 1, "this"
        );

        LoxFunction method = superclass.findMethod(expr.method.lexeme);

        if (method == null) {
            throw new RuntimeError(
                    expr.method,
                    "Undefined property '" + expr.method.lexeme + "'."
            );
        }

        return method.bind(object);
    }

    @Override
    public Object visitThisExpr(Expr.This expr) {
        return this.lookUpVariable(expr.keyword, expr);
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = this.evaluate(expr.right);

        return switch (expr.operator.type) {
            case BANG -> !this.isTruthy(right);
            case MINUS -> {
                this.checkNumberOperand(expr.operator, right);
                yield -(double) right;
            }
            // Unreachable.
            default -> null;
        };
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
/* Interpreting Resolved Variables 11.4
        return this.environment.get(expr.name);
*/
        return this.lookUpVariable(expr.name, expr);
    }

    private Object lookUpVariable(Token name, Expr expr) {
        Integer distance = this.locals.get(expr);
        if (distance != null) {
            return this.environment.getAt(distance, name.lexeme);
        } else {
            return this.globals.get(name);
        }
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;

        throw new RuntimeError(operator, "Operand must be numbers.");
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean) object;
        return true;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;

        if (a.equals(Double.NaN) && b.equals(Double.NaN)) {
            return false;
        }

        return a.equals(b);
    }

    private String stringify(Object object) {
        if (object == null) return "nil";

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }
}
