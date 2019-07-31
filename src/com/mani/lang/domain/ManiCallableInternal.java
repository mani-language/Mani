/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.domain;

import com.mani.lang.core.Interpreter;

import java.util.List;

/*public interface ManiCallableInternal {
    // arity is the amount of arguments the command needs.
    Object workWith = null;
    void setItem(Object item);
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}*/

public class ManiCallableInternal implements ManiCallable {

    public Object workWith = null;

    public void setItem(Object item) {
        this.workWith = item;
    }

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        return null;
    }
}