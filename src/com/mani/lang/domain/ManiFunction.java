package com.mani.lang.domain;

import com.mani.lang.enviroment.Environment;
import com.mani.lang.exceptions.Return;
import com.mani.lang.core.Interpreter;
import com.mani.lang.core.Stmt;

import java.util.List;

public class ManiFunction implements ManiCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    private final Boolean isInitializer;
    private final Boolean isPrivate;

    public ManiFunction(Stmt.Function declaration, Environment closure, Boolean isInitializer, Boolean isPrivate) {
        this.declaration = declaration;
        this.closure = closure;
        this.isInitializer = isInitializer;
        this.isPrivate = isPrivate;
    }

    ManiFunction bind(ManiInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new ManiFunction(declaration, environment, isInitializer, isPrivate);
    }

    public Boolean isPrivate() { return this.isPrivate; }

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((declaration == null) ? 0 : declaration.hashCode());
        result = prime * result + ((closure == null) ? 0 : closure.hashCode());
        result = prime * result + ((isInitializer == null) ? 0 : isInitializer.hashCode());
        result = prime * result + ((isPrivate == null) ? 0 : isPrivate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ManiFunction other = (ManiFunction) obj;
        if (declaration.name == null) {
            if (other.declaration.name != null) {
                return false;
            }
        } else if (!declaration.name.equals(other.declaration.name)) {
            return false;
        }
        if (isPrivate == null) {
            if (other.isPrivate != null) {
                return false;
            }
        } else if (!isPrivate.equals(other.isPrivate)) {
            return false;
        }
        if (closure == null) {
            if (other.closure != null) {
                return false;
            }
        } else if (!closure.equals(other.closure)) {
            return false;
        }
        if (isInitializer == null) {
            if (other.isInitializer != null) {
                return false;
            }
        } else if (!isInitializer.equals(other.isInitializer)) {
            return false;
        }
        return this.arity() == other.arity();
    }

    @Override
    public String toString() {
        return "<maniFn " + declaration.name.lexeme + ">" ;
    }
}