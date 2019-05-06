package com.mani.lang.Modules.sockets;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Std;
import com.mani.lang.local.Locals;
import io.socket.client.IO;
import io.socket.engineio.client.Socket;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

public class new_socket implements ManiCallable {
    @Override
    public int arity() {
        return -1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        try {
            final String url = arguments.get(0).toString();
            if (arguments.size() == 1) {
                return IO.socket(url);
            }

            if (!(Locals.getType(arguments.get(1)).equals("map"))) {
                System.err.println("Map expected in second argument");
                return null;
            }
            IO.Options options = parseOptions((HashMap<Object, Object>) arguments.get(1));
            return IO.socket(url, options);
        } catch (URISyntaxException ue) {
            return -1;
        }
    }

    private static IO.Options parseOptions(HashMap<Object, Object> db) {
        final IO.Options result = new IO.Options();

        if (db.containsKey("rememberUpgrade")) { result.rememberUpgrade = Std.DoubleToInt((Double) db.get("rememberUpgrade")) != 0; }
        if (db.containsKey("secure")) { result.secure = Std.DoubleToInt((Double) db.get("secure")) != 0; }
        if (db.containsKey("timestampRequests")) { result.timestampRequests = Std.DoubleToInt((Double) db.get("timestampRequests")) != 0; }
        if (db.containsKey("upgrade")) { result.upgrade = Std.DoubleToInt((Double) db.get("upgrade")) != 0; }

        if (db.containsKey("policyPort")) { result.policyPort = Std.DoubleToInt((Double) db.get("policyPort")); }
        if (db.containsKey("port")) { result.port = Std.DoubleToInt((Double) db.get("port")); }

        if (db.containsKey("host")) { result.host = String.valueOf(db.get("host")); }
        if (db.containsKey("hostname")) { result.hostname = String.valueOf(db.get("hostname")); }
        if (db.containsKey("path")) { result.path = String.valueOf(db.get("path")); }
        if (db.containsKey("query")) { result.query = String.valueOf(db.get("query")); }
        if (db.containsKey("timestampParam")) { result.timestampParam = String.valueOf(db.get("timestampParam")); }


        if (db.containsKey("transports")) {
            final List<Object> arr = (List<Object>) db.get("transports");
            final String[] values = new String[((List<Object>) db.get("transports")).size()];
            int index = 0;
            for (Object value : arr) {
                values[index++] = value.toString();
            }
            result.transports = values;
        }

        return result;
    }
}
