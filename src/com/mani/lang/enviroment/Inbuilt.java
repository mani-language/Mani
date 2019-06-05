package com.mani.lang.enviroment;

import com.mani.lang.main.Mani;
import com.mani.lang.main.Std;
import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Inbuilt {

    /**
     * This file creates a list called "inBuilts" that contains functions / API's that can be used by the stdlib
     * This is loaded before anything else.
     */


    public static Map<String, ManiCallable> inBuilts = new HashMap<>();
    static{

        inBuilts.put("ask", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                Scanner keyboard = new Scanner(System.in);
                String input = "";
                if (arguments.get(0) instanceof String) {
                    System.out.print(arguments.get(0));
                    input = keyboard.nextLine();
                } else {
                    return "ask : argument must be a string.";
                }
                return input;
            }
        });


        inBuilts.put("split", new ManiCallable() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                String item = arguments.get(0).toString().trim();
                return new ArrayList<String>(Arrays.asList(item.split(arguments.get(1).toString())));
            }
        });

        inBuilts.put("trim", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                String str = arguments.get(0).toString();
                return str.trim();
            }
        });

    }
}
