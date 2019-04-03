package com.mani.lang.Modules.files;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Modules.Module;

import java.io.File;
import java.util.List;

public class files implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("fopen", new files_open());
        interpreter.addSTD("fwrite", new files_write());
        interpreter.addSTD("fread", new files_read());
        interpreter.addSTD("fexists", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                File f = (File) arguments.get(0);
                return f.exists();
            }
        });

        interpreter.addSTD("fisFileOrDir", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                File f = (File) arguments.get(0);
                return f.isFile() ? "file" : f.isDirectory() ? "dir" : false;
            }
        });

        interpreter.addSTD("fcanWrite", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).canWrite();
            }
        });

        interpreter.addSTD("fcanRead", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).canRead();
            }
        });

        interpreter.addSTD("fcanExecute", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).canExecute();
            }
        });

        interpreter.addSTD("fgetPath", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).getAbsolutePath();
            }
        });
    }

    @Override
    public boolean hasExtensions() {
        return false;
    }

    @Override
    public Object extensions() {
        return null;
    }
}
