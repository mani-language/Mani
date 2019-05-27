package com.mani.lang.Modules.types;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.Modules.Module;
import com.mani.lang.main.Std;
import com.mani.lang.local.Locals;

import java.util.List;

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
                if (arguments.get(0).getClass().getSimpleName() == "Double") {
                    if (Math.round((double) arguments.get(0)) == Std.DoubleToInt((double) arguments.get(0))) {
                        // This means that its a whole number.
                        System.out.println("running");
                        arguments.set(0, Std.DoubleToInt(Math.round((double) arguments.get(0))));
                    }
                }
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
        interpreter.addSTD("isAlpha", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                char c = arguments.get(0).toString().charAt(0);
                return (c >= 'a' && c <= 'z') ||
                        (c >= 'A' && c <= 'Z') ||
                        c == '_';
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
                return Locals.getType(arguments.get(0));
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