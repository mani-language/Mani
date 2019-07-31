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
import com.mani.lang.main.Std;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class std_size implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        switch(arguments.get(0).getClass().getSimpleName().toLowerCase()) {
            case "string":
                return Std.makeDouble(arguments.get(0).toString().length());
            case "arraylist":
            case "list":
                ArrayList<Object> arrayGiven = (ArrayList<Object>) arguments.get(0);
                return Std.makeDouble(arrayGiven.size());
            case "hashmap":
                HashMap<Object, Object> given = (HashMap<Object, Object>) arguments.get(0);
                return Std.makeDouble(given.size());
        }
        return null;
	}

}