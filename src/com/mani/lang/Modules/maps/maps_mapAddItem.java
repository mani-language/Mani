package com.mani.lang.Modules.maps;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.HashMap;
import java.util.List;

public final class maps_mapAddItem implements ManiCallable {

    @Override
    public int arity() {
        return 3;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof HashMap)) {
            return "Please make sure your first argument is a map.";
        }
        HashMap<Object, Object> map = (HashMap<Object, Object>) arguments.get(0);
        map.put(arguments.get(1), arguments.get(2));
        return map;
	}

}