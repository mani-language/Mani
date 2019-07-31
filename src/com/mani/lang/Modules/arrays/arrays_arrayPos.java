/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.arrays;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.util.ArrayList;
import java.util.List;

public final class arrays_arrayPos implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof ArrayList)) {
            return "Please provide an array and an object you're searching for!";
        }
        double counter = 0;
        List<Object> arr = (List<Object>) arguments.get(0);
        for(Object obj : arr) {
            if (obj.equals(arguments.get(1))){
                return counter;
            }
            counter += 1;
        }
        return null;
	}

}