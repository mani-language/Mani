package com.mani.lang.Modules.atomic;

import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Modules.Module;

public final class atomic implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("AtomicBool", new atomic_boolean());
        interpreter.addSTD("ABSet", new atomic_boolean_set());
        interpreter.addSTD("ABCompareSet", new atomic_boolean_compareSet());
        interpreter.addSTD("ABGetSet", new atomic_boolean_getSet());
        interpreter.addSTD("ABGet", new atomic_boolean_get());
    }

}