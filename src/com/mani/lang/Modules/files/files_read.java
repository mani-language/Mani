package com.mani.lang.Modules.files;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
            File f = (File) arguments.get(0);

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
