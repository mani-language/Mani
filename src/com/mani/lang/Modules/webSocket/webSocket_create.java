package com.mani.lang.Modules.webSocket;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.util.List;

public class webSocket_create implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);
        try {
            return factory.createSocket((String) arguments.get(0));
        } catch (IOException e) {
            return null;
        }
    }
}
