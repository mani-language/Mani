package com.mani.lang.Modules.json;

import com.mani.lang.core.Interpreter;
import com.mani.lang.Modules.Module;

public final class json implements Module {
    
    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("json_parse", new json_parse());
        interpreter.addSTD("json_encode", new json_encode());
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