package com.mani.lang.Modules.maps;

import java.util.HashMap;
import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class maps_mapLength implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof HashMap)) {
            return "Please provide a map as the argument.";
        }
        HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);
        return given.size();
	}

}