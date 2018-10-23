package com.mani.lang.Modules.std;

import java.util.ArrayList;
import java.util.List;

import com.mani.lang.*;

public final class std_newarray implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
		switch((String) arguments.get(0)) {
            case "String":
                return new ArrayList<String>();
            case "Object":
                return new ArrayList<>();

        }
        return "Need to have atleast 1 argument";
	}

}