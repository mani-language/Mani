package com.mani.lang.Modules.std;

import com.mani.lang.Modules.Module;

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
        interpreter.addSTD("find", new ManiCallable(){
        
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ManiFunction returned = interpreter.getFunction(arguments.get(0).toString());
                returned.call(interpreter, arguments);
                return returned;
            }
        
            @Override
            public int arity() {
                return 1;
            }
        });
        /*
         * interpreter.addSTD("split", new std_split()); 
         * interpreter.addSTD("subString", new std_subString()); 
         */
        
    }

}