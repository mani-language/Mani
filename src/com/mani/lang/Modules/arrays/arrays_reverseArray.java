package com.mani.lang.Modules.arrays;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.ArrayList;
import java.util.List;

public final class arrays_reverseArray implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1) {
            return "Please provide an array to reverse.";
        }
        ArrayList<Object> arr = (ArrayList<Object>) arguments.get(0);
        ArrayList reversed = new ArrayList<>();
        int arrSize = arr.size();
        
        for (int i = 0; i < arrSize; i++) {
          reversed.add(arr.get(arrSize - i - 1));
        }

        return reversed;
	}

}
