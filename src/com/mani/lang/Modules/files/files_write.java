package com.mani.lang.Modules.files;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.io.*;
import java.util.List;

public class files_write implements ManiCallable {

    /**
     * 1 - File object.
     * 2 - What we are writing.
     * 3 - Mode.
     */
    @Override
    public int arity() {
        return 3;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        File file = (File) arguments.get(0);

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
            if (arguments.get(2) == "w") {
                PrintWriter p = new PrintWriter(file);
                p.write("");
                p.close();
            } else {
                pw.write((String) arguments.get(1));
                pw.close();
            }
        } catch (IOException e) {
            return "Failed to load file.";
        }

        return file;
    }
}
