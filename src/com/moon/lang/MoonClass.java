package com.moon.lang;

import java.util.List;
import java.util.Map;

class MoonClass implements MoonCallable {
    final String name;
    private final Map<String, MoonFunction> methods;
    final MoonClass superclass;

    MoonClass(String name, MoonClass superclass, Map<String, MoonFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    MoonFunction findMethod(MoonInstance instance, String name) {
        if(methods.containsKey(name)) return methods.get(name).bind(instance);

        if(superclass != null) return superclass.findMethod(instance, name);

        return null;
    }

    @Override
    public int arity() {
        MoonFunction initializer = methods.get(name);
        if(initializer == null) return 0;
        return initializer.arity();
    } 

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        MoonInstance instance = new MoonInstance(this);
        MoonFunction initializer = methods.get(name);
        if(initializer != null) initializer.bind(instance).call(interpreter, arguments);
        return instance;
    }

    @Override
    public String toString() {
        return name;
    }
}