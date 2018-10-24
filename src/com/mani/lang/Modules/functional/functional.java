package com.mani.lang.Modules.functional;

import com.mani.lang.Interpreter;
import com.mani.lang.Modules.Module;

public final class functional implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("arrayForEach", new f_arrayForEach());
	}

}