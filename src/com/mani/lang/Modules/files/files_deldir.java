package com.mani.lang.Modules.files;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class files_deldir implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        try {
            Files.delete(Paths.get((String) arguments.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
