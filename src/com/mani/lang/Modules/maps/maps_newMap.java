package com.mani.lang.Modules.maps;

import java.util.HashMap;
import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class maps_newMap implements ManiCallable {

    @Override
    public int arity() {
        return 0;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		return new HashMap<>();
	}

}