package com.mani.lang.Modules.maps;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.HashMap;
import java.util.List;

public final class maps_mapGetValue implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof HashMap)) {
            return "Please provide a map as your first argument.";
        }
        HashMap<Object, Object> map = (HashMap<Object, Object>) arguments.get(0);
        if (map.containsKey(arguments.get(1))) {
            return map.get(arguments.get(1));
        }
        return null;
	}

}