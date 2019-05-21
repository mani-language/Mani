package com.mani.lang.Modules.std;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.domain.ManiFunction;
import com.mani.lang.Modules.Module;
import com.mani.lang.main.Std;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class std implements Module{

    @Override
    public void init(Interpreter interpreter) {

        interpreter.define("ANSI_RESET", "\u001B[0m");
        interpreter.define("ANSI_BLACK", "\u001B[30m");
        interpreter.define("ANSI_RED", "\u001B[31m");
        interpreter.define("ANSI_GREEN", "\u001B[32m");
        interpreter.define("ANSI_YELLOW", "\u001B[33m");
        interpreter.define("ANSI_BLUE", "\u001B[34m");
        interpreter.define("ANSI_PURPLE", "\u001B[35m");
        interpreter.define("ANSI_CYAN", "\u001B[36m");
        interpreter.define("ANSI_WHITE", "\u001B[37m");
        interpreter.define("NL", "\n");
        interpreter.define("TAB", "\t");

        interpreter.addSTD("toString", new std_toString());
        interpreter.addSTD("toLowerCase", new std_toLowerCase());
        interpreter.addSTD("toUpperCase", new std_toUpperCase());
        interpreter.addSTD("charAt", new std_charAt());
        interpreter.addSTD("sleep", new std_sleep());
        interpreter.addSTD("sort", new std_sort());
        interpreter.addSTD("trim", new std_trim());
        interpreter.addSTD("toChar", new std_toChar());
        interpreter.addSTD("size", new std_size());
        interpreter.addSTD("time", new ManiCallable() {

            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Std.makeDouble((int) System.currentTimeMillis());
            }
        });
        interpreter.addSTD("find", new ManiCallable() {

            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ManiFunction returned = interpreter.getFunction(arguments.get(0).toString());
                returned.call(interpreter, arguments);
                return returned;
            }
        });
        interpreter.addSTD("split", new ManiCallable() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                String workWith = (String) arguments.get(0);
                String splitBy = (String) arguments.get(1);
                List<String> list = new ArrayList<String>(Arrays.asList(workWith.split(splitBy)));
                return list;
            }
        });

        interpreter.addSTD("exit", new ManiCallable(){
        
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                System.exit(Std.DoubleToInt((double) arguments.get(0)));
                return null;
            }
        });

        interpreter.addSTD("enviro", new ManiCallable() {

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                Map<String, String> db = System.getenv();
                if (db.containsKey(arguments.get(0))) {
                    return db.get(arguments.get(0));
                }
                return null;
            }
        });

        interpreter.addSTD("wipeMemory", new ManiCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                interpreter.wipeMemory();
                Std.loaded.clear();
                return null;
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
