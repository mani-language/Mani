/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.files;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class files_read implements ManiCallable {
    @Override
    public int arity() {
        return 1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        try {
            String data = "";
            File f = null;
            if (arguments.get(0) instanceof String) {
                f = new File((String)arguments.get(0));
            } else if (arguments.get(0) instanceof File) {
                f = (File) arguments.get(0);
            }

            Scanner fscan = new Scanner(f);

            if (f.exists()) {
                while (fscan.hasNextLine()) {
                    data += fscan.nextLine() + "\n";
                }
            }

            return data;

        } catch (FileNotFoundException e) {
            return "File not found";
        }

    }
}
