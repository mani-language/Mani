package com.mani.lang.Modules.threads;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.ManiCallableInternal;
import com.mani.lang.ManiFunction;
import com.mani.lang.Modules.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class threads implements Module {
    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("thread", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                ManiFunction callback = (ManiFunction) arguments.get(0);
                Thread thread = new Thread(() -> callback.call(interpreter, new ArrayList<Object>()));
                thread.start();
                return thread;
            }
        });

        interpreter.addSTD("sleep", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                try {
                    Thread.sleep(((Double) arguments.get(0)).longValue());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return null;
            }
        });
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("sleep", new ManiCallableInternal(){
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments){
                Thread thread = ((Thread) this.workWith);
                try {
                    thread.sleep((Long) arguments.get(0));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return null;
            }
        });

        db.put("thread", locals);
        return db;
    }
}
