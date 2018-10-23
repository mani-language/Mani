package com.mani.lang.Modules.std;

import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

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