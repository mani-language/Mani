package com.mani.lang.Modules.socket;

import com.mani.lang.Interpreter;
import com.mani.lang.Modules.Module;
import io.socket.client.Socket;

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
        return false;
    }

    @Override
    public Object extensions() {
        return null;
    }
}
