package com.mani.lang.Modules.atomic;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class atomic_boolean_getSet implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The actual Atomic Boolean
         * 2 -  What to set it too.
         */

        boolean prev;
        AtomicBoolean current = (AtomicBoolean) arguments.get(0);
        prev = current.get();
        current.set((boolean) arguments.get(1));

        return prev;
	}

}