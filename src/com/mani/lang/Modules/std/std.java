package com.mani.lang.Modules.std;

import com.mani.lang.Modules.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mani.lang.*;

public final class std implements Module{

    @Override
    public void init(Interpreter interpreter) {
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
                return Std.makeDouble(System.currentTimeMillis());
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

    }

}
