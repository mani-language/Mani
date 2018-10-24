package com.mani.lang.Modules.arrays;

import java.util.ArrayList;
import java.util.List;

import com.mani.lang.*;

public final class arrays_newArray implements ManiCallable {

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        return new ArrayList<>();
	}

}