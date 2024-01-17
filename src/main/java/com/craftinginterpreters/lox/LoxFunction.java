package com.craftinginterpreters.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    LoxFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public String toString() {
        return "<fn " + this.declaration.name.lexeme + ">";
    }

    @Override
    public int arity() {
        return this.declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
/* Local Function and Closure 10.6
        Environment environment = new Environment(interpreter.globals);
*/
        Environment environment = new Environment(this.closure);
        for (int i = 0; i < this.declaration.params.size(); i++) {
            environment.define(
                    this.declaration.params.get(i).lexeme, arguments.get(i)
            );
        }

/* Returning from calls 10.5.1
        interpreter.executeBlock(this.declaration.body, environment);
*/
        try {
            interpreter.executeBlock(this.declaration.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }
}