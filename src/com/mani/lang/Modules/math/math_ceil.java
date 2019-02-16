package com.mani.lang.Modules.math;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Std;

import java.util.List;

public class math_ceil implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        if (arguments.get(0) instanceof Double || arguments.get(0) instanceof Integer) {
            return Math.ceil((double) arguments.get(0));
        }
        return null;
    }
}
