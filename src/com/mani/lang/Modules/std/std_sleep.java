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

public final class std_sleep implements ManiCallable {

    @Override
    public int arity() {
        return 1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
		if (arguments.size() != 1 || (arguments.size() == 1 && !(arguments.get(0) instanceof Long))) {
            return "Requires long arguement to use.";
        }
        long num =  ((Long) arguments.get(0)).longValue();
        try{
        	Thread.sleep(num);
        } catch (InterruptedException e) {
			throw new RuntimeException("Exception: " + e.toString());
	    }
        return null;
	}

}