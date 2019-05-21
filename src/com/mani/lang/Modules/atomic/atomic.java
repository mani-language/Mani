package com.mani.lang.Modules.atomic;

import com.mani.lang.core.Interpreter;
import com.mani.lang.Modules.Module;

public final class atomic implements Module {

    @Override
    public void init(Interpreter interpreter) {
        // Everything for the atomic booleans
        interpreter.addSTD("AtomicBool", new atomic_boolean());
        interpreter.addSTD("ABSet", new atomic_boolean_set());
        interpreter.addSTD("ABCompareSet", new atomic_boolean_compareSet());
        interpreter.addSTD("ABGetSet", new atomic_boolean_getSet());
        interpreter.addSTD("ABGet", new atomic_boolean_get());

        // Everything for the atomic integers
        interpreter.addSTD("AtomicInt", new atomic_int());
        interpreter.addSTD("AISet", new atomic_int_set());
        interpreter.addSTD("AICompareSet", new atomic_int_compareSet());
        interpreter.addSTD("AIGet", new atomic_int_get());
        interpreter.addSTD("AIGetAdd", new atomic_int_getAdd());
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