/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.flatmap;

import com.mani.lang.Modules.Module;
import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.exceptions.GeneralError;

import java.util.HashMap;
import java.util.List;

public class flatmap implements Module {
    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("flatMap", new ManiCallable() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Double && arguments.get(1) instanceof Double)) {
                    throw new GeneralError("Arguments must be width and height in numbers!");
                }
                int width = ((Double) arguments.get(0)).intValue();
                int height = ((Double) arguments.get(1)).intValue();
                return new FlatMap(width, height);
            }
        });
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("at", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Double && arguments.get(1) instanceof Double)) {
                    throw new GeneralError("Arguments must be x and y, in numbers!");
                }
                int x = ((Double) arguments.get(0)).intValue();
                int y = ((Double) arguments.get(1)).intValue();
                return ((FlatMap) this.workWith).get(x, y);
            }
        });

        locals.put("put", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 3;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Double && arguments.get(1) instanceof  Double)) {
                    throw new GeneralError("Position arguments must be a number");
                }

                int x = ((Double) arguments.get(0)).intValue();
                int y = ((Double) arguments.get(1)).intValue();
                Object obj = arguments.get(2);

                ((FlatMap) this.workWith).put(x, y, obj);
                return null;
            }
        });

        db.put("flatmap", locals);
        return db;
    }

    public class FlatMap {
        int width;
        int height;
        Object[][] map;

        FlatMap(int width, int height) {
            this.width = width;
            this.height = height;
            this.map = new Object[this.width][this.height];
        }

        public Object get(int x, int y) {
            return this.map[x][y];
        }

        public void put(int x, int y, Object obj) {
            this.map[x][y] = obj;
        }
    }
}

