package com.mani.lang.Modules.atomic;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class atomic_boolean_set implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The atomic boolean we are working with.
         * 2 -  The Value we are setting it with.
         */
        
        AtomicBoolean val = (AtomicBoolean) arguments.get(0);
        val.set((boolean)arguments.get(1));

        return val;
	}

}