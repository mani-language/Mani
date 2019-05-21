package com.mani.lang.Enviroment;

import com.mani.lang.Mani;
import com.mani.lang.Std;
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

        
        inBuilts.put("date", new ManiCallable(){
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return new Date();
            }
        });

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

        inBuilts.put("pow", new ManiCallable() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                Double numPow;
                if ((arguments.get(0) instanceof Double) && (arguments.get(1) instanceof  Double)) {
                    numPow = Math.pow((Double)arguments.get(0), (Double)arguments.get(1));
                } else {
                    return "pow : both arguments need to be a number!";
                }
                return numPow;
            }
        });
        
        inBuilts.put("import", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                //TODO: Check for a ".mods" file in the current path. If it exists,
                // check to see what modules are in it... and be able to load them by name.
                /**
                 * EG. Lets say there is a module called "Frank" installed in the mani_mods directory...
                 * The user can use `import("Frank");` in their code... and it will auto take that file...
                 * And run it, (It will run a `Frank();` as it is the file name...
                 *
                 * If a `.mods` file doesnt exist, or does but does not contain what the user wants to import... we will
                 * check the standard library.
                 *
                 * Well... Thats the end goal...
                 */

                /**
                 * Section that checks to see if there is internet avaliable. If there is... it
                 * will use that online version over the Local version. Just so
                 * it is more up-to-date.
                 */

                // Before we start, we are going to convert to lowercase to prevent errors.
                arguments.set(0, ((String) arguments.get(0)).toLowerCase());

                if (Mani.hasInternet) {
                    // If there is internet, we will choose to use that STDLIB over the Local...
                    // simply due to the fact that it will be more up-to-date.
                    if (Std.find(arguments.get(0).toString()).equalsIgnoreCase("-2")){
                        // This means it has already been loaded.
                        return "Already loaded!";
                    } else {
                        try {
                            URL url = new URL("https://raw.githubusercontent.com/crazywolf132/Mani/master/stdlib/" + arguments.get(0) + ".mni");
                            Scanner s = new Scanner(url.openStream());
                            String final_file = "";
                            while(s.hasNextLine()){
                                final_file += s.nextLine() + "\n";
                            }
                            Std.loadFromUrl(interpreter, final_file);
                            return final_file;
                        }
                        catch(IOException ex) {
                            String res = Std.find(arguments.get(0).toString());
                            if (res.equalsIgnoreCase("-1")) {
                                System.err.println("No such library!");
                            } else {
                                return Std.loadFile(interpreter, res);
                            }
                        }
                    }
                } else {
                    // As there is no internet. We are going to have to try and use the Local version
                    // That is if the user actually has them installed...
                    if (arguments.get(0) instanceof String) {
                        if (!Std.hasRun) {
                            Std.Load();
                        }
                        String res = Std.find(arguments.get(0).toString());
                        if (res.equalsIgnoreCase("-1")) {
                            System.err.println("No such library!");
                        } else if (res.equalsIgnoreCase("-2")) {
                            return "Already loaded!";
                        } else {
                            return Std.loadFile(interpreter, res);
                        }
                    }
                }

                return "Expected argument 1 to be a String";
            }
        });

    }
}
