package com.mani.lang.Modules.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class maps_arraysToMap implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof ArrayList || arguments.get(1) instanceof ArrayList)) {
            return "Please provide 2 arrays as arguments.";
        }
        HashMap<Object, Object> joined = new HashMap<>();
        ArrayList<Object> first = (ArrayList<Object>) arguments.get(0);
        ArrayList<Object> second = (ArrayList<Object>) arguments.get(1);
        if (first.size() != second.size()) {
            System.err.println("Please make sure both arrays are the same size.");
            return "Please make sure both arrays are the same size.";
        }
        for (int i = 0; i < first.size(); i++) {
            joined.put(first.get(i), second.get(i));
        }
        return joined;
	}

}