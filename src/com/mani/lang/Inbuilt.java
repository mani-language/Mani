package com.mani.lang;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import javax.swing.*;

import com.mani.lang.Modules.Module;

public class Inbuilt {

    /**
     * This file creates a list called "inBuilts" that contains functions / API's that can be used by the stdlib
     * This is loaded before anything else.
     */


    public static Map<String, ManiCallable> inBuilts = new HashMap<>();
    
    static{
        inBuilts.put("find", new ManiCallable() {

            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return inBuilts.get(arguments.get(0).toString());
            }

        });

        inBuilts.put("use", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (arguments.size() != 1) {
                    System.out.println("Please provide 1 argument with what library to use.");
                    return "Please provide 1 argument with what library to use.";
                }
                try {
                    final String moduleName = arguments.get(0).toString();
                    final Module module = (Module) Class.forName("com.mani.lang.Modules." + moduleName + "." + moduleName).newInstance();
                    module.init(interpreter);
                    return null;
                } catch (Exception e) {
                    return "Couldn't load for some reason!";
                }
            }
        });

        inBuilts.put("readInt", new ManiCallable(){
            @Override
            public int arity() {
                return 0;
            }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                int number;
                Scanner scanner = new Scanner(System.in);
                try {
                    number = scanner.nextInt();
                    } catch(InputMismatchException e) {
                        return e.getMessage();
                    }
                    return number;
                }
            
        }); 

        inBuilts.put("readString", new ManiCallable(){
            @Override
            public int arity() {
                return 0;
            }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                String str;
                Scanner scanner =  new Scanner(System.in);
                try{
                    str = scanner.next();
                } catch(InputMismatchException e) {
                    return e.getMessage();
                }
                return str;
            }
        });
        
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

        inBuilts.put("getFile", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                String output = "";
                if(arguments.get(0) instanceof String){
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner( new File((String) arguments.get(0)) );
                    } catch (FileNotFoundException e) {
                        return "getFile : Error in opening file!";
                    }
                    output = scanner.useDelimiter("\\A").next();
                    scanner.close(); // Put this call in a finally block
                } else {
                    return "getFile : argument needs to be a string.";
                }
                return output;
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
        
        inBuilts.put("sqrt", new ManiCallable(){
            @Override
            public int arity() {
                return 1;
            }
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                Double numSqrt;
                if(arguments.get(0) instanceof Double) {
                    numSqrt = Math.sqrt((Double)arguments.get(0));
                } else {
                    return "sqrt : argument must be a number";
                }
                return numSqrt;
            }
        });


        inBuilts.put("load", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {

                if (arguments.get(0) instanceof String){
                    try {
                        byte[] bytes = Files.readAllBytes(Paths.get((String) arguments.get(0)));
                        Lexer lexer = new Lexer(new String(bytes, Charset.defaultCharset()));
                        List<Token> tokens = lexer.scanTokens();
                        Parser parser = new Parser(tokens);
                        List<Stmt> statements = parser.parse();
                        Resolver resolver = new Resolver(interpreter);
                        resolver.resolve(statements);
                        interpreter.interpret(statements);
                        return "Should have worked!";
                    } catch (IOException e) {
                        return "Oh shit!";
                    }
                }

                return "load : argument must be a string.";
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
                 * will use that online version over the local version. Just so
                 * it is more up-to-date.
                 */
                if (Mani.hasInternet) {
                    // If there is internet, we will choose to use that STDLIB over the local...
                    // simply due to the fact that it will be more up-to-date.
                    if (Std.find(arguments.get(0).toString()).equalsIgnoreCase("-2")){
                        // This means it has already been loaded.
                        return "Already loaded!";
                    } else {
                        try {
                            URL url = new URL("https://raw.githubusercontent.com/crazywolf132/Mani/master/stdlib/" + arguments.get(0) + ".mn");
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
                    // As there is no internet. We are going to have to try and use the local version
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
