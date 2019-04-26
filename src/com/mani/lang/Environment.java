package com.mani.lang;

import java.util.HashMap;
import java.util.Map;

class Environment {
    final Environment enclosing;
    private final Map<nspace, Object> values = new HashMap<>();

    String SPACE="namespace";

    Environment() {
        enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }


    void define(String name, Object value) {
        values.put(new nspace(SPACE, name), value);
    }

    Object getAt(int distance, String name) {
        return ancestor(distance).values.get(new nspace(SPACE, name));
    }

    void assignAt(int distance, Token name, Object value) {
        ancestor(distance).values.put(new nspace(SPACE, name.lexeme), value);
    }

    Environment ancestor(int distance) {
       Environment environment = this;
        for( int i = 0 ; i < distance ; i++ ) {
            environment = environment.enclosing;
       }
       return environment;
    }

    /**
     * Takes the variable that the user (the code) is looking for. It checks to see if it is defined in the enviroment
     * before returning it to whatever called this function. If it doesnt exist, then it will let the user know.
     *
     * @param name
     * @return
     */

    Object get(Token name) {
        if(values.containsKey(new nspace(SPACE, name.lexeme))) {
            return values.get(new nspace(SPACE, name.lexeme));
        }
        if(enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }

    Object get(String name) {
        
        if (values.containsKey(new nspace(SPACE, name))) {
            return values.get(new nspace(SPACE, name));
        }
        if (enclosing != null) return enclosing.get(name);
        throw new RuntimeError(null, "Undefined variable '" + name + "'.");
    }

    /**
     * Used to re-assign the values of already created variables in the enviroment.
     * @param name
     * @param value
     */

    void assign(Token name, Object value) {
        if(values.containsKey(new nspace(SPACE, name.lexeme))) {
            values.put(new nspace(SPACE, name.lexeme), value);
            return;
        }

        if(enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }

    void assignForce(Token name, Object value) {
        if (enclosing != null) {
            enclosing.assignForce(name, value);
            return;
        }

        values.put(new nspace(SPACE, name.lexeme), value);
    }

    void cleanupForce(Token name) {
        if (enclosing != null) {
            enclosing.cleanupForce(name);
            return;
        }
        values.remove(name);

    }
}