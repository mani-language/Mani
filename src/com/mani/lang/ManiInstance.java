package com.mani.lang;

import java.util.HashMap;
import java.util.Map;

class ManiInstance {

    private ManiClass klass;
    final private Map<String, Object> fields = new HashMap<>();

    ManiInstance(ManiClass klass) {
        this.klass = klass;
    } 

    Object get(Token name) {
        if(fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }
        
        ManiFunction method = klass.findMethod(this, name.lexeme);
        if(method != null) return method;
        throw new RuntimeError(name, "Undefined Property '" + name.lexeme +"'.");
    }

    void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}