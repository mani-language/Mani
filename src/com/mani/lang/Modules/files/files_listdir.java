package com.mani.lang.Modules.files;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class files_listdir implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        List<Object> db = new ArrayList<>();
        try {
            db = (List<Object>) Files.list(Paths.get((String) arguments.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return db;
    }
}
