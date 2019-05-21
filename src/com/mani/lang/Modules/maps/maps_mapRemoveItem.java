package com.mani.lang.Modules.maps;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.HashMap;
import java.util.List;

public final class maps_mapRemoveItem implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof HashMap)) {
            return "Please use a map as your first argument.";
        }
        HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);

        given.remove(arguments.get(1));
        return given;
	}

}