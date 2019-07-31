/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.java;

import com.mani.lang.Modules.Module;
import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;

public class java implements Module {
    @Override
    public void init(Interpreter interpreter) {
//        interpreter.addSTD("newClass",);
    }



    private static class JavaClass {

        public final Class<?> clazz;

        public JavaClass(Class<?> clazz) {
            this.clazz = clazz;
            init(clazz);
        }

        private void init(Class<?> clazz) {

        }
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
