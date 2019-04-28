package com.mani.lang.Modules.threads;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.ManiFunction;
import com.mani.lang.Modules.Module;

import java.util.ArrayList;
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
