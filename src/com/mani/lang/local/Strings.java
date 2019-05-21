package com.mani.lang.local;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallableInternal;

import java.util.HashMap;
import java.util.List;

public class Strings {

    public static HashMap<String, ManiCallableInternal> locals = new HashMap<>();

    static {
        locals.put("startsWith", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((String) arguments.get(0)).startsWith((String) arguments.get(0));
            }
        });
    }

}
