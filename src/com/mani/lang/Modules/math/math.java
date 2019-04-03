package com.mani.lang.Modules.math;

import com.mani.lang.Interpreter;
import com.mani.lang.Modules.Module;
import com.mani.lang.Modules.std.std_toString;

public class math implements Module {
    @Override
    public void init(Interpreter interpreter) {
        // Defining common Math variables.
        interpreter.define("PI", Math.PI);
        interpreter.define("E", Math.E);

        // Defining the functions.
        interpreter.addSTD("floor", new math_floor());
        interpreter.addSTD("ceil", new math_ceil());
        interpreter.addSTD("rand", new math_rand());
    }

    @Override
    public boolean hasExtensions() {
        return false;
    }

    @Override
    public Object extensions() {
        return null;
    }
}
