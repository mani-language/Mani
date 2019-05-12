package com.mani.lang.Modules.webSocket;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallableInternal;
import com.mani.lang.ManiFunction;
import com.mani.lang.Modules.Module;
import com.mani.lang.local.Locals;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class webSocket implements Module {
    @Override
    public void init(Interpreter interpreter) {
        // Dispatches an event.
        interpreter.define("DISPATCH", "0");
        // Used for ping checking.
        interpreter.define("HEARTBEAT", "1");
        // Used for client handshake.
        interpreter.define("IDENTIFY", "2");
        // Used to update the client status.
        interpreter.define("STATUS_UPDATE", "3");
        // Used to join/move/leave voice channels.
        interpreter.define("VOICE_STATE_UPDATE", "4");
        // Used for voice ping checking.
        interpreter.define("VOICE_SERVER_PING", "5");
        // Used to resume a closed connection.
        interpreter.define("RESUME", "6");
        // Used to tell clients to reconnect to the gateway.
        interpreter.define("RECONNECT", "7");
        // Used to request guild members.
        interpreter.define("REQUEST_GUILD_MEMBERS", "8");
        // Used to notify client they have an invalid session id.
        interpreter.define("INVALID_SESSION", "9");
        // Sent immediately after connecting, contains heartbeat and server debug information.
        interpreter.define("HELLO", "10");
        // Sent immediately following a client heartbeat that was received.
        interpreter.define("HEARTBEAT_ACK", "11");

        interpreter.addSTD("newSocket", new webSocket_create());
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("on", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                // Register a listener to receive WebSocket events.
                final ManiFunction callBack = (ManiFunction) arguments.get(0);
                ((WebSocket) this.workWith).addListener(new WebSocketAdapter() {
                    @Override
                    public void onTextMessage(WebSocket websocket, String message) throws Exception {
                        // Received a text message.......
                        List<Object> args = new ArrayList<>();
                        args.add(message);
                        callBack.call(interpreter, args);
                    }
                });
                return null;
            }
        });

        locals.put("open", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                try {
                    ((WebSocket) this.workWith).connect();
                } catch (WebSocketException e) {
                    System.err.println("Could not connect to server!");
                    return null;
                }
                return null;
            }
        });

        locals.put("isOpen", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((WebSocket) this.workWith).isOpen();
            }
        });

        locals.put("sendPing", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((WebSocket) this.workWith).sendPing();
                return null;
            }
        });

        locals.put("sendPong", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((WebSocket) this.workWith).sendPong();
                return null;
            }
        });

        locals.put("getState", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((WebSocket) this.workWith).getState();
            }
        });

        locals.put("setHeaders", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(Locals.getType(arguments.get(0)).equalsIgnoreCase("map"))) {
                    System.err.println("Argument must be a map!");
                    return null;
                }
                for (Object header : ((HashMap<Object, Object>) arguments.get(0)).keySet()) {
                    ((WebSocket) this.workWith).addHeader((String) header, (String) (((HashMap<Object, Object>) this.workWith).get(header)));
                }
                return null;
            }
        });

        locals.put("clearHeaders", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((WebSocket) this.workWith).clearHeaders();
                return null;
            }
        });

        locals.put("close", new ManiCallableInternal() {
           @Override
           public Object call(Interpreter interpreter, List<Object> arguments) {
               ((WebSocket) this.workWith).disconnect();
               return null;
           }
        });

        locals.put("send", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((WebSocket) this.workWith).sendText((String) arguments.get(0));
                return null;
            }
        });


        db.put("webSocket", locals);
        return db;
    }
}
