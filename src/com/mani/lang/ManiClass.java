package com.mani.lang;

import java.util.List;
import java.util.Map;

class ManiClass implements ManiCallable {
    public final String name;
    private final Map<String, ManiFunction> methods;
    final ManiClass superclass;

    ManiClass(String name, ManiClass superclass, Map<String, ManiFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    ManiFunction findMethod(ManiInstance instance, String name) {
        if(methods.containsKey(name)) return methods.get(name).bind(instance);

        if (superclass != null) {
            ManiFunction result = superclass.findMethod(instance, name);
            if (result != null && !result.isPrivate()) {
                return result;
            } else if (result != null && result.isPrivate()) {
                throw new RuntimeError(new Token(null, name, result, 0), "Sorry, this is a private function!");
            }
        }

        return null;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int arity() {
        ManiFunction initializer = methods.get(name);
        if(initializer == null) return 0;
        return initializer.arity();
    } 

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        ManiInstance instance = new ManiInstance(this);
        ManiFunction initializer = methods.get(name);
        if(initializer != null) initializer.bind(instance).call(interpreter, arguments);
        return instance;
    }

    @Override
    public String toString() {
        return name;
    }
}