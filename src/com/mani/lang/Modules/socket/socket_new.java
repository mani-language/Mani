package com.mani.lang.Modules.socket;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Std;
import com.mani.lang.local.Locals;
import io.socket.client.IO;
import io.socket.engineio.client.Socket;

import java.net.URISyntaxException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

public class socket_new implements ManiCallable {
    @Override
    public int arity() {
        return -1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {

        try {
            final String url = (String) arguments.get(0);

            if (!(arguments.size() <= 2 || arguments.size() >= 1)) {
                System.err.println("Arguments must be 1 or 2. Address and options map.");
                return null;
            }

            if (arguments.size() == 2 && !(arguments.get(1) instanceof HashMap)) {
                System.err.println("Argument #2 must be a map!");
            }

            IO.Options options = null;

            if (arguments.size() == 2 && arguments.get(1) instanceof HashMap) {
                options = parseOptions((HashMap<Object, Object>) arguments.get(1));
            }

            return IO.socket(url, options);
        } catch(URISyntaxException ue) {
            return null;
        }

    }


    private static IO.Options parseOptions(HashMap<Object, Object> db) {
        IO.Options result = new IO.Options();

        if (db.containsKey("forceNew")) { result.forceNew = Std.DoubleToInt((Double) db.get("forceNew")) != 0; }
        if (db.containsKey("multiplex")) { result.multiplex = Std.DoubleToInt((Double) db.get("multiplex")) != 0; }
        if (db.containsKey("reconnection")) { result.reconnection = Std.DoubleToInt((Double) db.get("reconnection")) != 0; }
        if (db.containsKey("rememberUpgrade")) { result.rememberUpgrade = Std.DoubleToInt((Double) db.get("rememberUpgrade")) != 0; }
        if (db.containsKey("secure")) { result.secure = Std.DoubleToInt((Double) db.get("secure")) != 0; }
        if (db.containsKey("timestampRequests")) { result.timestampRequests = Std.DoubleToInt((Double) db.get("timestampRequests")) != 0; }
        if (db.containsKey("upgrade")) { result.upgrade = Std.DoubleToInt((Double) db.get("upgrade")) != 0; }

        if (db.containsKey("policyPort")) { result.policyPort = Std.DoubleToInt((Double) db.get("policyPort")); }
        if (db.containsKey("port")) { result.port = Std.DoubleToInt((Double) db.get("port")); }
        if (db.containsKey("reconnectionAttempts")) { result.reconnectionAttempts = Std.DoubleToInt((Double) db.get("reconnectionAttempts")); }

        if (db.containsKey("reconnectionDelay")) { result.reconnectionDelay = new Long(String.valueOf(db.get("reconnectionDelay"))); }
        if (db.containsKey("reconnectionDelayMax")) { result.reconnectionDelayMax = new Long(String.valueOf(db.get("reconnectionDelayMax"))); }
        if (db.containsKey("timeout")) { result.timeout = new Long(String.valueOf(db.get("timeout"))); }

        if (db.containsKey("randomizationFactor")) { result.randomizationFactor = (Double) db.get("randomizationFactor"); }

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
