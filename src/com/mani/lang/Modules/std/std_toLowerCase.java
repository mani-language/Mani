package com.mani.lang.Modules.std;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;

public final class std_toLowerCase implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        if (arguments.size() != 1 || (arguments.size() == 1 && !(arguments.get(0) instanceof String))) {
            return "Requires string arguement to use.";
        }
        return arguments.get(0).toString().toLowerCase();
    }

}