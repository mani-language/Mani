package com.mani.lang.Modules.files;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.io.File;
import java.util.List;

public class files_open implements ManiCallable {

    /**
     * 1 - File name.
     */
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        final File fopen = new File(arguments.get(0).toString());

        return fopen;
    }
}
