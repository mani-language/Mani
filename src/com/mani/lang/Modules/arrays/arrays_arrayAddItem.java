package com.mani.lang.Modules.arrays;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.ArrayList;
import java.util.List;

public final class arrays_arrayAddItem implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 2) {
            return "Please provide an array, and what you wish to add as arguments.";
        }
        ArrayList<Object> arr = (ArrayList<Object>) arguments.get(0);
        arr.add(arguments.get(1));
        return arr;
	}

}