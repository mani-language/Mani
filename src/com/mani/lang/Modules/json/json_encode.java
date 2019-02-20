package com.mani.lang.Modules.json;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;

import org.json.JSONObject;
import org.json.JSONException;

public class json_encode implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        try {
            final Object root = arguments.get(0);
            final String jsonRaw = JSONObject.valueToString(root);
            return jsonRaw;
        } catch (JSONException e) {
            System.out.println(e);
            System.exit(1);
        }
        return "";
    }
}
