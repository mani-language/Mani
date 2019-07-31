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

public final class arrays_reverseArray implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        ArrayList<Object> arr = (ArrayList<Object>) arguments.get(0);
        ArrayList reversed = new ArrayList<>();
        int arrSize = arr.size();

        for (int i = 0; i < arrSize; i++) {
          reversed.add(arr.get(arrSize - i - 1));
        }

        return reversed;
	}

}
