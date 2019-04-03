package com.mani.lang.local;

import com.mani.lang.ManiCallable;
import com.mani.lang.ManiCallableInternal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locals {

    private static HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();

    static {
        HashMap<String, ManiCallableInternal> inner = new HashMap<>();
        for (String item : Lists.locals.keySet()) {
            inner.put(item, Lists.locals.get(item));
        }
        db.put("list", inner);
    }

    public static boolean canWorkWith(Object workWith, String lookingFor) {
        String type = getType(workWith);

        return (type == null ? false : functionFound(type, lookingFor));
    }

    private static boolean functionFound(String type, String lookingFor) {
        HashMap<String, ManiCallableInternal> funcs = db.get(type);
        return funcs.containsKey(lookingFor);
    }

    private static String getType(Object workWith) {
        if (workWith instanceof List) {
            return "list";
        } else if (workWith instanceof Double) {
            return "number";
        } else if (workWith instanceof Map || workWith instanceof HashMap) {
            return "map";
        } else if (workWith instanceof String) {
            return "string";
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
