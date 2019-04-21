package com.mani.lang.Modules.atomic;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class atomic_int_set implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The atomic int
         * 2 -  What to set it to.
         */

        AtomicInteger val = (AtomicInteger) arguments.get(0);
        val.set(new Double((Double) arguments.get(1)).intValue());
        return null;
	}

}