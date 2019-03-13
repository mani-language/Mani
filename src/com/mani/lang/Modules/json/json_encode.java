package com.mani.lang.Modules.json;

import com.google.gson.JsonElement;
import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;


import com.google.gson.Gson;

public class json_encode implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Gson gson = new Gson();
        String str = gson.fromJson((JsonElement) arguments.get(0), String.class);
        return "";
    }
}
