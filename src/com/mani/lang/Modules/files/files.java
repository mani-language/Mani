package com.mani.lang.Modules.files;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.Modules.Module;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class files implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("fopen", new files_open());
        interpreter.addSTD("fwrite", new files_write());
        interpreter.addSTD("fread", new files_read());
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
        interpreter.addSTD("flistDir", new files_listdir());
        interpreter.addSTD("fmkdir", new files_mkdir());
        interpreter.addSTD("fdel", new files_deldir());
        interpreter.addSTD("fexists", new files_dirExists());
    }

    @Override
    public boolean hasExtensions() {
        return false;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("exists", new ManiCallableInternal() {
           @Override
           public Object call(Interpreter interpreter, List<Object> arguments) {
               return ((File) arguments.get(0)).exists();
           }
        });

        locals.put("canWrite", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).canWrite();
            }
        });

        locals.put("canRead", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).canRead();
            }
        });

        locals.put("canExecute", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((File) arguments.get(0)).canExecute();
            }
        });

        db.put("file", locals);
        return db;
    }
}
