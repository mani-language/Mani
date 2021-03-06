/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.std;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.Collections;
import java.util.List;

public final class std_sort implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1) {
            return "Please provide 1 arguement with an array";
        }
        if (arguments.get(0) instanceof String) {
            List<String> db = (List<String>) arguments.get(0);
            Collections.sort(db);
            return db;
        } else if (arguments.get(0) instanceof Double) {
            List<Double> db = (List<Double>) arguments.get(0);
            Collections.sort(db);
        }
        return "This function can only be used for String or Number arrays!";
    }

}