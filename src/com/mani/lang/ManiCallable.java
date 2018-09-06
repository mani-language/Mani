package com.mani.lang;

import java.util.List;

interface ManiCallable {
    // arity is the amount of arguments the command needs.
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}