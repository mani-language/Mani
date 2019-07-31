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
import com.mani.lang.main.Mani;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class maps_arraysToMap implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (!(arguments.get(0) instanceof ArrayList || arguments.get(1) instanceof ArrayList)) {
            return "Please provide 2 arrays as arguments.";
        }
        HashMap<Object, Object> joined = new HashMap<>();
        ArrayList<Object> first = (ArrayList<Object>) arguments.get(0);
        ArrayList<Object> second = (ArrayList<Object>) arguments.get(1);
        if (first.size() != second.size()) {
            Mani.printAndStoreError("Please make sure both arrays are the same size.");
            return "Please make sure both arrays are the same size.";
        }
        for (int i = 0; i < first.size(); i++) {
            joined.put(first.get(i), second.get(i));
        }
        return joined;
	}

}