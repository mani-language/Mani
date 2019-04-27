package com.mani.lang.Modules.socket;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallableInternal;
import com.mani.lang.ManiFunction;
import com.mani.lang.Modules.Module;
import io.socket.client.Socket;

import java.util.HashMap;
import java.util.List;

public class socket implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.define("EVENT_CONNECT", Socket.EVENT_CONNECT);
        interpreter.define("EVENT_CONNECTING", Socket.EVENT_CONNECTING);
        interpreter.define("EVENT_CONNECT_ERROR", Socket.EVENT_CONNECT_ERROR);
        interpreter.define("EVENT_CONNECT_TIMEOUT", Socket.EVENT_CONNECT_TIMEOUT);
        interpreter.define("EVENT_DISCONNECT", Socket.EVENT_DISCONNECT);
        interpreter.define("EVENT_ERROR", Socket.EVENT_ERROR);
        interpreter.define("EVENT_MESSAGE", Socket.EVENT_MESSAGE);
        interpreter.define("EVENT_PING", Socket.EVENT_PING);
        interpreter.define("EVENT_PONG", Socket.EVENT_PONG);
        interpreter.define("EVENT_RECONNECT", Socket.EVENT_RECONNECT);
        interpreter.define("EVENT_RECONNECTING", Socket.EVENT_RECONNECTING);
        interpreter.define("EVENT_RECONNECT_ATTEMPT", Socket.EVENT_RECONNECT_ATTEMPT);
        interpreter.define("EVENT_RECONNECT_ERROR", Socket.EVENT_RECONNECT_ERROR);
        interpreter.define("EVENT_RECONNECT_FAILED", Socket.EVENT_RECONNECT_FAILED);

        interpreter.addSTD("newSocket", new socket_new());
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("close", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Socket) this.workWith).close();
                return null;
            }
        });

        locals.put("connect", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Socket) this.workWith).connect();
                return null;
            }
        });

        locals.put("connected", new ManiCallableInternal() {
           @Override
           public Object call(Interpreter interpreter, List<Object> arguments) {
               return ((Socket) this.workWith).connected();
           }
        });

        locals.put("emit", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
               ((Socket) this.workWith).emit((String) arguments.get(0), arguments.get(1));
               return null;
            }
        });

        locals.put("open", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Socket) this.workWith).open();
                return null;
            }
        });

        db.put("socket", locals);
        return db;
    }

    private void executeSocketListener(ManiFunction listener, List<Object> args) {

    }
}
