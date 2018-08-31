package com.moon.lang;

import java.util.List;

class MoonFunction implements MoonCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    private final Boolean isInitializer;

    MoonFunction(Stmt.Function declaration, Environment closure, Boolean isInitializer) {
        this.declaration = declaration;
        this.closure = closure;
        this.isInitializer = isInitializer;
    }

    MoonFunction bind(MoonInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new MoonFunction(declaration, environment, isInitializer);
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for(int i = 0 ; i < declaration.parameters.size() ; i++) {
            environment.define(declaration.parameters.get(i).lexeme, arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch(Return returnValue) {
            if(isInitializer) return environment.getAt(0, "this");
            return returnValue.value;
        }

        if(isInitializer) return environment.getAt(0, "this");
        return null;
    }

    @Override 
    public int arity() {
        return declaration.parameters.size();
    }

    @Override
    public String toString() {
        return "<moonFn " + declaration.name.lexeme + ">" ;
    }
}