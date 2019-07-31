/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.atomic;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class atomic_int implements ManiCallable {

    @Override
    public int arity() {
        return 0;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		return new AtomicInteger();
	}

}