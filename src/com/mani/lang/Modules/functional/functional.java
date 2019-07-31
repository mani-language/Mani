/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.functional;

import com.mani.lang.core.Interpreter;
import com.mani.lang.Modules.Module;

public final class functional implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("arrayForEach", new f_arrayForEach());
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