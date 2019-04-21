package com.mani.lang.Modules.atomic;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class atomic_boolean_get implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The actual atomic Boolean.
         */
        AtomicBoolean val = (AtomicBoolean) arguments.get(0);
        return val.get();
	}

}