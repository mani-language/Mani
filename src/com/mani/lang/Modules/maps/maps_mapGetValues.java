package com.mani.lang.Modules.maps;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class maps_mapGetValues implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        if (!(arguments.get(0) instanceof HashMap)) {
            return "Please make sure you have a map as your argument.";
        }
        ArrayList<Object> values = new ArrayList<>();
        HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);
        for (Object key : given.keySet()) {
            values.add(given.get(key));
        }
        return values;
    }

}
