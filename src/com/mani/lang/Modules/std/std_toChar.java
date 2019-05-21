package com.mani.lang.Modules.std;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;

/**
 * For converting an Ascii value to a character.
 */
public final class std_toChar implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        int num = new Double((double) arguments.get(0)).intValue();
		return (char) num;
	}

}