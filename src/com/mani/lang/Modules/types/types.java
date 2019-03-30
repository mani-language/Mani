package com.mani.lang.Modules.types;

import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Modules.Module;

public final class types implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("toAscii", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                char character = (char) arguments.get(0).toString().charAt(0);
                int pos = character;
                return new Double(pos);
            }
        });
        interpreter.addSTD("toNumber", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }
            
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                try{
                    return new Double(arguments.get(0).toString());
                } catch(Exception e) {
                    return "Could not convert to number";
                }
            }
        });
        interpreter.addSTD("toString", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return arguments.get(0).toString();
            }
        });
        interpreter.addSTD("toChar", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return (char) arguments.get(0);
            }
        });
        interpreter.addSTD("isDigit", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (arguments.get(0).getClass().getSimpleName() == "Char") {
                    char c = (char) arguments.get(0);
                    return c >= '0' && c <= '9';
                }
                return arguments.get(0).getClass().getSimpleName() == "Double";
            }
        });
        interpreter.addSTD("isChar", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return arguments.get(0).getClass().getSimpleName() == "Char";
            }
        });
        interpreter.addSTD("isString", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return arguments.get(0).getClass().getSimpleName() == "String";
            }
        });
        interpreter.addSTD("isBool", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return arguments.get(0).getClass().getSimpleName() == "Boolean";
            }
        });
        interpreter.addSTD("getType", new ManiCallable(){
        
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (arguments.size() != 1) {
                    return "Please provide a single arguement of an object to check.";
                }
                switch (arguments.get(0).getClass().getSimpleName()) {
                case "String":
                    return "String";
                case "Double":
                    return "Number";
                case "ManiFunction":
                    return "Function";
                case "Boolean":
                    return "Boolean";
                }
                return arguments.get(0).getClass().getSimpleName();
            }
        });
	}

}