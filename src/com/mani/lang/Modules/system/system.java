/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.system;

import com.mani.lang.Modules.Module;
import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.main.Mani;
import com.mani.lang.main.Std;

import java.util.List;

public class system implements Module {
    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("System.online", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Boolean)) {
                    Mani.printAndStoreError("Argument must be true or false!");
                    return null;
                }
                Mani.hasInternet = (Boolean) arguments.get(0);
                return null;
            }
        });

        interpreter.addSTD("System.compiled", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Boolean)) {
                    Mani.printAndStoreError("Argument must be true or false!");
                    return null;
                }
                Mani.compiledMode = (Boolean) arguments.get(0);
                return null;
            }
        });

//        interpreter.addSTD("stdlib_path", new ManiCallable() {
//            @Override
//            public int arity() {
//                return 1;
//            }
//
//            @Override
//            public Object call(Interpreter interpreter, List<Object> arguments) {
//                if (!(arguments.get(0) instanceof String)) {
//                    Mani.printAndStoreError("Argument must be a string!");
//                    return null;
//                }
//                Mani.stdlib_path = (String) arguments.get(0);
//                return true;
//            }
//        });

        interpreter.addSTD("System.hadError", new ManiCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Mani.hadRuntimeError;
            }
        });

        interpreter.addSTD("System.latestError", new ManiCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Mani.latestErrorMsg;
            }
        });
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
