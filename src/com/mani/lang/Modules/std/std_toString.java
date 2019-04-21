package com.mani.lang.Modules.std;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;

public final class std_toString implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1) {
            return "Please provide atleast 1 argument to convert to string";
        }
        return arguments.get(0).toString();
	}

}