package com.mani.lang.enviroment;

import com.mani.lang.domain.Namespace;
import com.mani.lang.exceptions.RuntimeError;
import com.mani.lang.token.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    public final Environment enclosing;

    /*
       Updated  Map<String, Object> so it now uses a custom Namespace object to specify each value
       Wherever "values" was being assigned with name and value, it is now assigned with Namespace and value
     */
    private final Map<Namespace, Object> values = new HashMap<>();

    public String defaultNamespace = "default";
    String loadedNamespace = defaultNamespace;

    public Environment() {
        enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public void switchNamespace(String namespace) {
        this.loadedNamespace = namespace;
    }

    public void define(String name, Object value) {
//        values.put(name, value);
        values.put(new Namespace(loadedNamespace, name), value);
    }

    public Object getAt(int distance, String name) {
//        return ancestor(distance).values.get(name);
        return ancestor(distance).values.get(new Namespace(loadedNamespace, name));
    }

    public void assignAt(int distance, Token name, Object value) {
//        ancestor(distance).values.put(name.lexeme, value);
        ancestor(distance).values.put(new Namespace(loadedNamespace, name.lexeme), value);
    }

    public Environment ancestor(int distance) {
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

    public Object get(Token name) {
//        if(values.containsKey(name.lexeme)) {
//            return values.get(name.lexeme);
        if (values.containsKey(new Namespace(loadedNamespace, name.lexeme))) {
            return values.get(new Namespace(loadedNamespace, name.lexeme));
        }
        if(enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }

    public Object get(String name) {
        
        if (values.containsKey(name)) {
            return values.get(new Namespace(loadedNamespace, name));
        }
        if (enclosing != null) return enclosing.get(name);
        throw new RuntimeError(null, "Undefined variable '" + name + "'.");
    }

    /**
     * Used to re-assign the values of already created variables in the enviroment.
     * @param name
     * @param value
     */

    public void assign(Token name, Object value) {
//        if(values.containsKey(name.lexeme)) {
//            values.put(name.lexeme, value);
        if (values.containsKey(new Namespace(loadedNamespace, name.lexeme))) {
            values.put(new Namespace(loadedNamespace, name.lexeme), value);
            return;
        }

        if(enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }

    public void assignForce(Token name, Object value) {
        if (enclosing != null) {
            enclosing.assignForce(name, value);
            return;
        }

//        values.put(name.lexeme, value);
        values.put(new Namespace(loadedNamespace, name.lexeme), value);
    }

    public void cleanupForce(Token name) {
        if (enclosing != null) {
            enclosing.cleanupForce(name);
            return;
        }
        values.remove(name);

    }

    public void wipe() {
        values.clear();
    }
}