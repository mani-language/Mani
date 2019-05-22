package com.mani.lang.enviroment;

import com.mani.lang.exceptions.RuntimeError;
import com.mani.lang.token.Token;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD:src/com/mani/lang/enviroment/Environment.java
public class Environment {
    public final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();
=======
class Environment {
    final Environment enclosing;
//    private final Map<String, Object> values = new HashMap<>();

    /*
       Updated  Map<String, Object> so it now uses a custom Namespace object to specify each value
       Wherever "values" was being assigned with name and value, it is now assigned with Namespace and value
     */

    private final Map<Namespace, Object> values = new HashMap<>();

    String defaultNamespace = "default";
    String loadedNamesapce = defaultNamespace;
>>>>>>> origin/Namespaces:src/com/mani/lang/Environment.java

    public Environment() {
        enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    void switchNamespace(String namespace) {
        this.loadedNamesapce = namespace;
    }

<<<<<<< HEAD:src/com/mani/lang/enviroment/Environment.java
    public void define(String name, Object value) {
        values.put(name, value);
    }

    public Object getAt(int distance, String name) {
        return ancestor(distance).values.get(name);
    }

    public void assignAt(int distance, Token name, Object value) {
        ancestor(distance).values.put(name.lexeme, value);
=======
    void define(String name, Object value) {
//        values.put(name, value);
        values.put(new Namespace(defaultNamespace, name), value);
    }

    Object getAt(int distance, String name) {
//        return ancestor(distance).values.get(name);
        return ancestor(distance).values.get(new Namespace(defaultNamespace, name));
    }

    void assignAt(int distance, Token name, Object value) {
//        ancestor(distance).values.put(name.lexeme, value);
        ancestor(distance).values.put(new Namespace(defaultNamespace, name.lexeme), value);
>>>>>>> origin/Namespaces:src/com/mani/lang/Environment.java
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

<<<<<<< HEAD:src/com/mani/lang/enviroment/Environment.java
    public Object get(Token name) {
        if(values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
=======
    Object get(Token name) {
//        if(values.containsKey(name.lexeme)) {
//            return values.get(name.lexeme);
//        }
        if (values.containsKey(new Namespace(defaultNamespace, name.lexeme))) {
            return values.get(new Namespace(defaultNamespace, name.lexeme));
>>>>>>> origin/Namespaces:src/com/mani/lang/Environment.java
        }
        if(enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }

    public Object get(String name) {
        
//        if (values.containsKey(name)) {
//            return values.get(name);
//        }
        if (values.containsKey(name)) {
            return values.get(new Namespace(defaultNamespace, name));
        }
        if (enclosing != null) return enclosing.get(name);
        throw new RuntimeError(null, "Undefined variable '" + name + "'.");
    }

    /**
     * Used to re-assign the values of already created variables in the enviroment.
     * @param name
     * @param value
     */

<<<<<<< HEAD:src/com/mani/lang/enviroment/Environment.java
    public void assign(Token name, Object value) {
        if(values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
=======
    void assign(Token name, Object value) {
//        if(values.containsKey(name.lexeme)) {
//            values.put(name.lexeme, value);
//            return;
//        }
        if (values.containsKey(new Namespace(defaultNamespace, name.lexeme))) {
            values.put(new Namespace(defaultNamespace, name.lexeme), value);
>>>>>>> origin/Namespaces:src/com/mani/lang/Environment.java
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
        values.put(new Namespace(defaultNamespace, name.lexeme), value);
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