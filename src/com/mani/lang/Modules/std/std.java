package com.mani.lang.Modules.std;

import com.mani.lang.Modules.Module;

import java.util.List;

import com.mani.lang.*;

public final class std implements Module{

    @Override
    public void init(Interpreter interpreter) {
        //interpreter.addSTD("newMap", new std_newMap());
        interpreter.addSTD("toString", new std_toString());
        interpreter.addSTD("toLowerCase", new std_toLowerCase());
        interpreter.addSTD("toUpperCase", new std_toUpperCase());
        interpreter.addSTD("charAt", new std_charAt());
        interpreter.addSTD("sleep", new std_sleep());
        interpreter.addSTD("sort", new std_sort());
        interpreter.addSTD("trim", new std_trim());
        interpreter.addSTD("getType", new std_getType());
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
         * interpreter.addSTD("split", new std_split()); interpreter.addSTD("subString",
         * new std_subString()); interpreter.addSTD("mapGetValue", new
         * std_mapGetValue()); interpreter.addSTD("mapAddItem", new std_mapAddItem());
         * interpreter.addSTD("mapRemoveItem", new std_mapRemoveItem());
         * interpreter.addSTD("mapUpdateItem", new std_mapUpdateItem());
         * interpreter.addSTD("mapGetKeys", new std_mapGetKeys());
         * interpreter.addSTD("arraysToMap", new std_arraysToMap());
         * i
         */
        
    }

}