package com.mani.lang.Modules.files;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class files_write implements ManiCallable {

    /**
     * 1 - File object.
     * 2 - What we are writing.
     * 3 - true for append, false for write blank file
     */
    @Override
    public int arity() {
        return 3;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        File file = (File) arguments.get(0);

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, (Boolean) arguments.get(2)));
            pw.write((String) arguments.get(1));
            pw.flush();
            pw.close();
        } catch (IOException e) {
            return "Failed to load file.";
        }

        return file;
    }
}
