package com.mani.lang.Modules.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class json_parse implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<Map<Object, Object>>(){}.getType();
        Map<Object, Object> theMap = gson.fromJson((String) arguments.get(0), type);
        return theMap;
    }
}