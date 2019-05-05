package com.mani.lang.Modules.socket;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallableInternal;
import com.mani.lang.ManiFunction;
import com.mani.lang.Modules.Module;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class socket implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.define("EVENT_CONNECT", Socket.EVENT_OPEN);
        interpreter.define("EVENT_HEART_BEAT", Socket.EVENT_HEARTBEAT);
        interpreter.define("EVENT_DISCONNECT", Socket.EVENT_CLOSE);
        interpreter.define("EVENT_ERROR", Socket.EVENT_ERROR);
        interpreter.define("EVENT_MESSAGE", Socket.EVENT_MESSAGE);
        interpreter.define("EVENT_PING", Socket.EVENT_PING);
        interpreter.define("EVENT_PONG", Socket.EVENT_PONG);

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
                ((Socket) this.workWith).open();
                return null;
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

        locals.put("send", new ManiCallableInternal() {
           @Override
           public int arity() { return 1; }

           @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
               ((Socket) this.workWith).send((String)arguments.get(0));
               return null;
           }
        });

        locals.put("on", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                final String event = arguments.get(0).toString();
                final ManiFunction listener = ((ManiFunction) arguments.get(1));
                List<Object> fnargs = new ArrayList<>();
                List<Object> nothing = new ArrayList<>();

                ((Socket) this.workWith).on(event, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        for (Object arg : args) {
                            nothing.add(arg);
                        }
                        fnargs.add(nothing);
                        listener.call(interpreter, fnargs);
                    }
                });
                return null;
            }
        });

        db.put("socket", locals);
        return db;
    }
}
