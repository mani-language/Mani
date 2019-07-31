/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.maps;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.HashMap;
import java.util.List;

public final class maps_mapKeyExists implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof HashMap)) {
            return "Please provide a map as the first argument.";
        }
        HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);
        for (Object obj : given.keySet()) {
            if (obj.equals(arguments.get(1))) {
                return true;
            }
        }
        return false;

	}

}