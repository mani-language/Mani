package com.mani.lang.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mani.lang.Modules.logger.logger;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.domain.ManiFunction;
import com.mani.lang.domain.ManiInstance;
import com.mani.lang.Modules.munit.Tester;

import java.io.File;
import com.neovisionaries.ws.client.WebSocket;
import io.socket.client.Socket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locals {

    private static HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();

    public static boolean canWorkWith(Object workWith, String lookingFor) {
        String type = getType(workWith);
        return (type == null ? false : functionFound(type, lookingFor));
    }

    public static boolean areFunctions(Object workWith) {
//        return (!(db.get(getType(workWith)).isEmpty()));
        //TODO: Fix this, just need to return false if there is no functions.
        return db.containsKey(getType(workWith)) && db.get(getType(workWith)).size() != 0;
    }

    private static boolean functionFound(String type, String lookingFor) {
        HashMap<String, ManiCallableInternal> funcs = db.get(type);
        if (funcs == null) {
            return false;
        }
        return funcs.containsKey(lookingFor);
//        return false;
    }

    public static String getType(Object workWith) {
        if (workWith instanceof List || workWith instanceof ArrayList) {
            return "list";
        } else if (workWith instanceof Double) {
            return "number";
        } else if (workWith instanceof Map || workWith instanceof HashMap) {
            return "map";
        } else if (workWith instanceof String) {
            return "string";
        } else if (workWith instanceof Boolean) {
            return "boolean";
        } else if (workWith instanceof File) {
            return "file";
        } else if (workWith instanceof Thread) {
            return "thread";
        } else if (workWith instanceof Tester) {
            return "tester";
        } else if (workWith instanceof WebSocket) {
            return "webSocket";
        } else if (workWith instanceof Socket) {
            return "socket";
        } else if (workWith instanceof logger.Logman) {
            return "logger";
        } else if (workWith instanceof ManiFunction) {
            return "function";
        } else if (workWith instanceof ManiInstance) {
            return ((ManiInstance) workWith).getClassName();
        }
        return null;
    }

    public static Boolean canConvert(Object left, Object right) {
        String toType = right.toString().toLowerCase();
        String fromType = getType(left).toLowerCase();
        switch (toType) {
            case "string":
                if (fromType.equals("string")) { return true; }
                if (fromType.equals("number")) { return true; }
                if (fromType.equals("list")) { return true; }
                if (fromType.equals("map")) { return true; }
                if (fromType.equals("file")) { return true; }
            case "number":
                if (fromType.equals("number")) { return true; }
                if (fromType.equals("string")) { return true; }
                if (fromType.equals("list")) { return true; }
                if (fromType.equals("map")) { return true; }
            case "list":
                if (fromType.equals("list")) { return true; }
                if (fromType.equals("map")) { return true; }
                if (fromType.equals("number")) { return true; }
                if (fromType.equals("string")) { return true; }
            case "map":
                if (fromType.equals("map")) { return true; }
                if (fromType.equals("string")) { return true; }
        }
        return false;
    }

    public static Object convert(Object left, Object right) {
        String toType = right.toString().toLowerCase();
        String fromType = getType(left).toLowerCase();
        switch(toType) {
            case "string":
                if (fromType.equals("string") || fromType.equals("number")) {
                    return String.valueOf(left);
                }
                if (fromType.equals("list")) {
                    String str = "";
                    int i = 0;
                    for (Object obj : ((ArrayList<Object>) left)) {
                        if (i != 0) {
                            str += ", ";
                        }
                        str += obj;
                        i++;
                    }
                    return str;
                }
                if (fromType.equals("map")) {
                    String str = "";
                    int i = 0;
                    for (Object key : ((HashMap<Object, Object>) left).keySet()) {
                        if (i != 0) {
                            str += ", ";
                        }
                        str += "key: " + key + " : val: " + ((HashMap<Object, Object>) left).get(key);
                        i++;
                    }
                    return str;
                }
                if (fromType.equals("file")) {
                    return ((File) left).toString();
                }
            case "number":
                if (fromType.equals("number")) { return left; }
                if (fromType.equals("string")) { return ((String) left).length(); }
                if (fromType.equals("list")) { return ((ArrayList<Object>) left).size(); }
                if (fromType.equals("map")) { return ((HashMap<Object, Object>) left).keySet().size(); }
            case "list":
                if (fromType.equals("string")) {
                    ArrayList<Object> to = new ArrayList<>();
                    boolean splitting = false;
                    if (((String) left).contains(" ")) {
                        splitting = true;
                    }
                    for (Object obj : ((String) left).split((splitting ? " " : ""))) {
                        to.add(obj);
                    }
                    return to;
                }
                if (fromType.equals("map")) {
                    ArrayList<Object> to = new ArrayList<>();
                    for (Object keys : ((HashMap<Object, Object>) left).keySet()) {
                        to.add(keys);
                        to.add(((HashMap<Object, Object>) left).get(keys));
                    }
                    return to;
                }
                if (fromType.equals("number")) {
                    String version = Double.toString((Double) left);
                    ArrayList<Object> to = new ArrayList<>();
                    for (char chr : version.toCharArray()) {
                        to.add(chr);
                    }

                    return to;
                }
                if (fromType.equals("list")) { return left; }
            case "map":
                if (fromType.equals("map")) { return left; }
                if (fromType.equals("string")) {
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<Map<Object, Object>>(){}.getType();
                    Map<Object, Object> theMap = gson.fromJson(left.toString(), type);
                    return theMap;
                }
        }
        return null;
    }

    public static ManiCallable getFunction(Object workWith, String lookingFor) {
        HashMap<String, ManiCallableInternal> funcs = db.get(getType(workWith));
        return funcs.get(lookingFor);
    }

    public static void add(Object list) {
        HashMap<String, HashMap<String, ManiCallableInternal>> latest = (HashMap<String, HashMap<String, ManiCallableInternal>>) list;
        HashMap<String, ManiCallableInternal> inner = new HashMap<>();
        for (String item : latest.keySet()) {
            for (String key : latest.get(item).keySet()) {
                inner.put(key, latest.get(item).get(key));
            }
            latest.put(item, inner);
        }

        db.putAll(latest);
    }
}
