/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.maps;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.Modules.Module;

import java.util.HashMap;
import java.util.List;

public final class maps implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("newMap", new maps_newMap());
        interpreter.addSTD("mapGetValue", new maps_mapGetValue());
        interpreter.addSTD("mapAddItem", new maps_mapAddItem());
        interpreter.addSTD("mapRemoveItem", new maps_mapRemoveItem());
        interpreter.addSTD("mapUpdateItem", new maps_mapUpdateItem());
        interpreter.addSTD("mapGetKeys", new maps_mapGetKeys());
        interpreter.addSTD("arraysToMap", new maps_arraysToMap());
        interpreter.addSTD("mapKeyExists", new maps_mapKeyExists());
        interpreter.addSTD("mapGetValues", new maps_mapGetValues());
        interpreter.addSTD("mapFind", new maps_mapFind());
	}

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("count", new ManiCallableInternal() {

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return (double) ((HashMap<Object, Object>) this.workWith).size();
            }
        });

        locals.put("has", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((HashMap<Object, Object>) this.workWith).keySet().contains(arguments.get(0));
            }
        });

        locals.put("add", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((HashMap<Object, Object>) this.workWith).put(arguments.get(0), arguments.get(1));
            }
        });

        locals.put("del", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((HashMap<Object, Object>) this.workWith).remove(arguments.get(0));
            }
        });

        locals.put("at", new ManiCallableInternal() {
           @Override
           public int arity() { return 1; }

           @Override
           public Object call(Interpreter interpreter, List<Object> arguments) {
               return ((HashMap<Object, Object>) this.workWith).get(arguments.get(0));
           }
        });

        db.put("map", locals);
        return db;
    }

}
