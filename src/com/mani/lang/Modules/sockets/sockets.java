/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.sockets;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.domain.ManiFunction;
import com.mani.lang.Modules.Module;
import io.socket.client.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sockets implements Module {

    boolean logger = true;

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

        interpreter.addSTD("newSocket", new new_socket());
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("open", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Socket) this.workWith).open();
                if (logger)
                    System.out.println("Connection opened");
                return null;
            }
        });

        locals.put("logging", new ManiCallableInternal() {
           @Override
           public int arity() { return 1; }

           @Override
           public Object call(Interpreter interpreter, List<Object> arguments) {
               if (arguments.get(0) instanceof Boolean) {
                   logger = (boolean) arguments.get(0);
               }
               return null;
           }
        });

        locals.put("id", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((Socket) this.workWith).id();
            }
        });

        locals.put("on", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
               final String event = arguments.get(0).toString();
               final ManiFunction listener = (ManiFunction) arguments.get(1);

                ((Socket) this.workWith).on(event, sArgs -> {
                    executeSocketListener(interpreter, listener, sArgs);
                });
                return null;
            }
        });

        locals.put("emit", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                final String event = arguments.get(0).toString();
                final Object msg = arguments.get(1);
                ((Socket) this.workWith).emit(event, msg);
                return null;
            }
        });

        locals.put("send", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                final Object msg = arguments.get(0);
                ((Socket) this.workWith).send(msg);
                return null;
            }
        });

        locals.put("close", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Socket) this.workWith).close();
                return null;
            }
        });

        db.put("socket", locals);
        return db;
    }

    private void executeSocketListener(Interpreter interpreter, ManiFunction listener, Object[] sArgs) {
        List<Object> args = new ArrayList<>();
        if (sArgs == null) {
            listener.call(interpreter, args);
        } else {
            int size = sArgs.length;
            for (int i = 0; i < size; i++) {
                args.add(sArgs[i]);
            }
            listener.call(interpreter, args);
        }
    }
}
