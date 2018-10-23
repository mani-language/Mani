package com.mani.lang.Modules.std;

import java.util.Collections;
import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class std_sort implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1) {
            return "Please provide 1 arguement with an array";
        }
        if (arguments.get(0) instanceof String) {
            List<String> db = (List<String>) arguments.get(0);
            Collections.sort(db);
            return db;
        } else if (arguments.get(0) instanceof Double) {
            List<Double> db = (List<Double>) arguments.get(0);
            Collections.sort(db);
        }
        return "This function can only be used for String or Number arrays!";
    }

}