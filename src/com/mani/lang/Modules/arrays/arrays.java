package com.mani.lang.Modules.arrays;

import com.mani.lang.Interpreter;
import com.mani.lang.Modules.Module;

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
        return null;
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

}