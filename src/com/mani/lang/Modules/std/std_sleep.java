package com.mani.lang.Modules.std;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;

public final class std_sleep implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        try {
            Thread.sleep((long) arguments.get(0));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return true;
	}

}