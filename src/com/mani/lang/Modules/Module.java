package com.mani.lang.Modules;

import com.mani.lang.core.Interpreter;

public interface Module {
    void init(Interpreter interpreter);

    boolean hasExtensions();

    Object extensions();
}