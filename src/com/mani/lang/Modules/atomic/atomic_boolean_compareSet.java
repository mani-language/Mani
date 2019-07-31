/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.atomic;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class atomic_boolean_compareSet implements ManiCallable {

    @Override
    public int arity() {
        return 3;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments
         * 1 -  The atomic boolean
         * 2 -  The condition.
         * 3 -  The value to set it to, if true.
         */

        AtomicBoolean val = (AtomicBoolean) arguments.get(0);
        boolean stmt = (boolean) arguments.get(1);
        if (stmt) {
            val.set((boolean) arguments.get(2));
        }
        return null;
	}

}