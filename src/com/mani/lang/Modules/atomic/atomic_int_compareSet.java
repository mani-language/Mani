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
import java.util.concurrent.atomic.AtomicInteger;

public final class atomic_int_compareSet implements ManiCallable {

    @Override
    public int arity() {
        return 3;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The atomic Integer
         * 2 -  Statement
         * 3 -  What to set it to
         */
        boolean usingBool = arguments.get(1) instanceof Boolean;
        boolean stmt;
        int Expected;  
        
        AtomicInteger val = (AtomicInteger) arguments.get(0);
        Integer toSet = new Double( (Double) arguments.get(2)).intValue();

        if (usingBool) {
            stmt = (boolean) arguments.get(1);
            if (stmt) {
                val.set(toSet);
            }
        } else {
            Expected = (Integer) arguments.get(1);
            val.compareAndSet(Expected, toSet);
        }

        return val;
	}

}