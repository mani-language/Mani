package com.mani.lang.Modules.maps;

import java.util.HashMap;
import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class maps_mapKeyExists implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof HashMap)) {
            return "Please provide a map as the first argument.";
        }
        HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);
        for (Object obj : given.keySet()) {
            if (obj == arguments.get(1)) {
                return true;
            }
        }
        return false;

	}

}