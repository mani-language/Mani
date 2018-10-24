package com.mani.lang.Modules.std;

import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class std_getType implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1) {
            return "Please provide a single arguement of an object to check.";
        }
        switch(arguments.get(0).getClass().getSimpleName()) {
            case "String":
                return "is a string";
            case "Double":
                return "is a double";
            case "ManiFunction":
                return "is a function";
            case "Boolean":
                return "is a boolean";
        }
        return null;
	}

}