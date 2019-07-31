/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.munit;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.Modules.Module;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.local.Locals;

import java.util.HashMap;
import java.util.List;

public class munit implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("newTester", new ManiCallable() {
            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return new Tester();
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

        locals.put("header", new ManiCallableInternal() {
            @Override
            public int arity() { return 1; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Tester) this.workWith).setHeader((String) arguments.get(0));
                return null;
            }
        });

        locals.put("assertEquals", new ManiCallableInternal() {
            @Override
            public int arity() { return 3; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Tester) this.workWith).assertEquals(arguments.get(0), arguments.get(1), (String) arguments.get(2));
                return null;
            }
        });

        locals.put("assertType", new ManiCallableInternal() {
            @Override
            public int arity() { return 3; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Tester) this.workWith).assertEquals(Locals.getType(arguments.get(0)), Locals.getType(arguments.get(1)), (String) arguments.get(2));
                return null;
            }
        });

        locals.put("assertTrue", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Tester) this.workWith).assertTrue(arguments.get(0), (String) arguments.get(1));
                return null;
            }
        });

        locals.put("assertFalse", new ManiCallableInternal() {
            @Override
            public int arity() { return 2; }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Tester) this.workWith).assertFalse(arguments.get(0), (String) arguments.get(1));
                return null;
            }
        });

        locals.put("results", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((Tester) this.workWith).getResults();
                return null;
            }
        });

        db.put("tester", locals);
        return db;
    }
}
