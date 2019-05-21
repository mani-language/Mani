package com.mani.lang.Modules.functional;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.domain.ManiFunction;

import java.util.ArrayList;
import java.util.List;

public final class f_arrayForEach implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 2 || (arguments.size() == 2 && !(arguments.get(0) instanceof List && arguments.get(1) instanceof ManiFunction))) {
            return "Arguments must be array and function.";
        }
        ArrayList<Object> list = (ArrayList<Object>) arguments.get(0);
        ManiFunction run = (ManiFunction) arguments.get(1);
        for (Object obj : list) {
            List<Object> db = new ArrayList<>();
            db.add(obj);
            run.call(interpreter, db);
        }
        return null;
	}

}