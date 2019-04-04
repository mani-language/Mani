package com.mani.lang.Modules.arrays;

import java.util.HashMap;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallableInternal;
import com.mani.lang.Modules.Module;
import com.mani.lang.Std;
import java.util.ArrayList;
import java.util.List;

public final class arrays implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("newArray", new arrays_newArray());
        interpreter.addSTD("arrayAddItem", new arrays_arrayAddItem());
        interpreter.addSTD("arrayPos", new arrays_arrayPos());
        //interpreter.addSTD("arrayRemoveItem", new arrays_arrayRemoveItem());
        //interpreter.addSTD("arrayUpdateItem", new arrays_arrayUpdateItem());
        //interpreter.addSTD("arrayDelItem", new arrays_arrayDelItem());
	}

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("at", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (this.workWith instanceof ArrayList) {
                    if (Std.DoubleToInt((double) arguments.get(0)) > ((ArrayList) this.workWith).size() - 1
                            || Std.DoubleToInt((double) arguments.get(0)) < 0) {
                        System.err.println("Range must be between 0 and " + (((ArrayList) this.workWith).size() - 1));
                        return null;
                    }
                    return ((ArrayList) this.workWith).get(Std.DoubleToInt((double) arguments.get(0)));
                } else if (this.workWith instanceof HashMap) {
                    return ((HashMap) this.workWith).get(arguments.get(0));
                }
                return null;
            }
        });

        locals.put("count", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((ArrayList) this.workWith).size();
            }
        });

        locals.put("add", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((ArrayList) this.workWith).add(arguments.get(0));
            }
        });

        locals.put("del", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Double)) {
                    System.err.println("Position must be a number!");
                    return null;
                }
                return ((ArrayList) this.workWith).remove(Std.DoubleToInt((double) arguments.get(0)));
            }
        });

        locals.put("clear", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ((ArrayList) this.workWith).clear();
                return null;
            }
        });

        locals.put("posOf", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((ArrayList) this.workWith).indexOf(arguments.get(0));
            }
        });

        locals.put("snip", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.get(0) instanceof Double)) {
                    System.err.println("Size must be a number");
                    return null;
                }
                List<Object> fin = new ArrayList<>();
                for (int i = 0; i < Std.DoubleToInt((double) arguments.get(0)); i++) {
                    fin.add(((ArrayList) this.workWith).get(i));
                }
                this.workWith = fin;
                return this.workWith;
            }
        });

        db.put("list", locals);
        return db;
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

}