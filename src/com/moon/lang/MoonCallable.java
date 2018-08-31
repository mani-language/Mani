package com.moon.lang;

import java.util.List;

interface MoonCallable {
    // arity is the amount of arguments the command needs.
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}