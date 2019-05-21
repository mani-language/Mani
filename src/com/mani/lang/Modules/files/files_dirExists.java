package com.mani.lang.Modules.files;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class files_dirExists implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        return Files.exists(Paths.get((String) arguments.get(0)));
    }
}
