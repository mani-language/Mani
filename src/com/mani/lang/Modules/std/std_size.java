package com.mani.lang.Modules.std;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Std;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class std_size implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        switch(arguments.get(0).getClass().getSimpleName().toLowerCase()) {
            case "string":
                return Std.makeDouble(arguments.get(0).toString().length());
            case "arraylist":
            case "list":
                ArrayList<Object> arrayGiven = (ArrayList<Object>) arguments.get(0);
                return Std.makeDouble(arrayGiven.size());
            case "hashmap":
                HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);
                return Std.makeDouble(given.size());
        }
        return null;
	}

}