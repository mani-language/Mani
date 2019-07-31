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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class maps_mapFind implements ManiCallable {

    @Override
    public int arity() {
        return 3;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        if (!(arguments.get(0) instanceof HashMap)) {
            return "Please make sure you have a map as your argument.";
        } else if (!(arguments.get(1) instanceof String)) {
            return "Please make sure you have a string as your key argument.";
        }

        HashMap<Object, HashMap<Object, Object>> given = (HashMap<Object, HashMap<Object, Object>>) arguments.get(0);
        Object keyParam = arguments.get(1);

        ArrayList<Object> keys = new ArrayList<>();

        for (Object key : given.keySet()) {
            keys.add(key);
        }

        HashMap<Object, Object> indexedValue;
        for (Object key : keys) {
            if (!(given.get(key) instanceof HashMap)) continue;
            indexedValue = given.get(key);
            if (indexedValue != null && indexedValue.get(keyParam) != null){
              if (indexedValue.get(keyParam).equals(arguments.get(2))) return indexedValue;
            }
        }

        return null;
    }

}
