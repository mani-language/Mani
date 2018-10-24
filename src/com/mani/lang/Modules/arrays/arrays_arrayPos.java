package com.mani.lang.Modules.arrays;

import java.util.ArrayList;
import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class arrays_arrayPos implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof ArrayList)) {
            return "Please provide an array and an object you're searching for!";
        }
        double counter = 0;
        List<Object> arr = (List<Object>) arguments.get(0);
        for(Object obj : arr) {
            if (obj.equals(arguments.get(1))){
                return counter;
            }
            counter += 1;
        }
        return null;
	}

}