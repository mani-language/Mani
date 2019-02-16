package com.mani.lang.Modules.math;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;

public class math_floor implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        if (arguments.get(0) instanceof Double || arguments.get(0) instanceof Integer) {
            return Math.floor((double) arguments.get(0));
        }
        return null;
    }
}
