package com.mani.lang.Modules.std;

import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class std_trim implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1 || ( arguments.size() == 1 && !(arguments.get(1) instanceof String))) {
            return "Please provide 1 arguement, a string";
        }
        return arguments.get(0).toString().trim();
	}

}