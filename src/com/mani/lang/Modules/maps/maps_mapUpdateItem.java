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

public final class maps_mapUpdateItem implements ManiCallable {

    @Override
    public int arity() {
        return 3;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        if (!(arguments.get(0) instanceof HashMap)) {
            return "Please make sure your first argument is a map.";
        }
        HashMap<Object, Object> map = (HashMap<Object, Object>) arguments.get(0);
        if (map.containsKey(arguments.get(1))) {
            map.put(arguments.get(1), arguments.get(2));
            return map;
        }
        return arguments.get(1).toString() + ", does not exist in the map.";
	}

}