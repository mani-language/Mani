package com.mani.lang.Modules.json;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import org.json.JSONException;
import org.json.JSONTokener;

public final class json_parse implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object class(Interpreter interpreter, List<Object> arguments) {
        String jsonRaw = arguments.get(0).toString();
        Object res = new JSONTokener(jasonRaw).nextValue();
        return res;
    }
}