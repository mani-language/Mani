package com.mani.lang.Modules.atomic;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class atomic_int_getAdd implements ManiCallable {

    @Override
    public int arity() {
        return 3;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        /**
         * Arguments:
         * 1 -  The atomic int
         * 2 -  Amount to add
         * 3 -  Return prev
         */

        AtomicInteger val = (AtomicInteger) arguments.get(0);
        Integer toAdd = new Double((Double) arguments.get(1)).intValue();
        boolean returnPrev = (boolean) arguments.get(2);

        val.addAndGet(toAdd);

        // A cheat way we can do to return the previous...
        // is to minus the added from the current.
        if (returnPrev) {
            return val.get() - toAdd;
        }

		return val.get();
	}

}