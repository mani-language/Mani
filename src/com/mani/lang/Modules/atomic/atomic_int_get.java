package com.mani.lang.Modules.atomic;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class atomic_int_get implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The atomic int.
         */
        AtomicInteger val = (AtomicInteger) arguments.get(0);
        return val.get();
	}

}