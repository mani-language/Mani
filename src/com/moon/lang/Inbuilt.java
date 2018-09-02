package com.moon.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Inbuilt {
    public static Map<String, MoonCallable> inBuilts  = new HashMap<>();
    static{

    inBuilts.put("str", new MoonCallable() {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            return arguments.get(0).toString();
        }
    });

    inBuilts.put("readInt", new MoonCallable(){
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

    inBuilts.put("readString", new MoonCallable(){
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
    
    inBuilts.put("date", new MoonCallable(){
        @Override
        public int arity() {
            return 0;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            return new Date();
        }
    });
    
    inBuilts.put("random", new MoonCallable(){
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            Random rand = new Random();
            int number;
            if(arguments.get(0) instanceof Double) {
                number = rand.nextInt(((Double)arguments.get(0)).intValue());
            } else {
                return "random : argument must be a number.";
            }
            return number;
        }
    });

    inBuilts.put("randRange", new MoonCallable() {
        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            Random rand = new Random();
            int max = new Double((Double) arguments.get(1)).intValue();
            int min = new Double((Double) arguments.get(0)).intValue();
            return new Double(rand.nextInt(max - min + 1) + min);
        }
    });

    inBuilts.put("ask", new MoonCallable() {
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

    inBuilts.put("getFile", new MoonCallable() {
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

    inBuilts.put("split", new MoonCallable() {
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

    inBuilts.put("pow", new MoonCallable() {
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
    
    inBuilts.put("sqrt", new MoonCallable(){
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

    inBuilts.put("dict", new MoonCallable() {
        @Override
        public int arity() {
            return 0;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            List<String> db = new ArrayList<>();
            db.add("Wowza");
            return db;
        }
    });

    inBuilts.put("list", new MoonCallable() {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            switch((String) arguments.get(0)) {
                case "String":
                    List<String> db = new ArrayList<String>();
                    return db;
                case "Object":
                    List<Object> odb = new ArrayList<Object>();
                    return odb;
            }
            return null;
        }
    });

    inBuilts.put("listAdd", new MoonCallable() {
        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.get(1) instanceof String) {
                List<String> db;
                db = (List<String>) arguments.get(0);
                db.add(arguments.get(1).toString());
                return db;
            } else if (arguments.get(1) instanceof MoonInstance || arguments.get(1) instanceof Object) {
                List<Object> db;
                db = (List<Object>) arguments.get(0);
                db.add(arguments.get(1));
                return db;
            } else if (arguments.get(1) instanceof Integer) {
                List<Integer> db;
                db = (List<Integer>) arguments.get(0);
                db.add(new Double((Double) arguments.get(1)).intValue());
            }

            return "nope";
        }
    });

    inBuilts.put("listReplace", new MoonCallable() {
        @Override
        public int arity() {
            return 3;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            List<Object> db;
            db = (List<Object>) arguments.get(0);
            db.set(new Double((double) arguments.get(1)).intValue(), arguments.get(2));
            return db;
        }
    });

    inBuilts.put("listDel", new MoonCallable() {
        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            List<Object> db;
            db = (List<Object>) arguments.get(0);
            db.remove(new Double((double) arguments.get(1)).intValue());
            return db;
        }
    });

    inBuilts.put("objSize", new MoonCallable() {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.get(0) instanceof ArrayList) {
                List<Object> db;
                db = (List<Object>) arguments.get(0);
                return new Double(db.size());
            } else if (arguments.get(0) instanceof HashMap) {
                HashMap<Object, Object> db;
                db = (HashMap<Object, Object>) arguments.get(0);
                return new Double(db.size());
            }
            return "objSize : Expecting List or Map as first argument!";
        }
    });

    inBuilts.put("getItem", new MoonCallable() {
        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.get(0) instanceof ArrayList) {
                List<String> db;
                db = (List<String>) arguments.get(0);

                return db.get(new Double((Double) arguments.get(1)).intValue());
            } else if(arguments.get(0) instanceof HashMap) {
                Map<String, String> db;
                db = (Map<String, String>) arguments.get(0);
                return db.get(arguments.get(1));
            }
            return "getItem : please make sure argument 1 is a list, and argument 2 is an int.";
        }
    });

    inBuilts.put("map", new MoonCallable() {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            switch ((String) arguments.get(0)) {
                case "String":
                    Map<String, String> db = new HashMap<>();
                    return db;
                case "Object":
                    Map<String, Object> odb = new HashMap<>();
                    return odb;
            }
            return null;
        }
    });

    inBuilts.put("mapAdd", new MoonCallable() {
        @Override
        public int arity() {
            return 3;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.get(2) instanceof String) {
                Map<String, String> db;
                db = (Map<String, String>) arguments.get(0);
                db.put(arguments.get(1).toString(), arguments.get(2).toString());
            } else if (arguments.get(2) instanceof Object) {
                Map<String,Object> db;
                db = (Map<String, Object>) arguments.get(0);
                db.put(arguments.get(1).toString(), arguments.get(2));
                return db;
            }
            return null;
        }
    });

    inBuilts.put("mapFind", new MoonCallable() {
        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.get(0) instanceof HashMap) {
                Map<String, String> db;
                db = (Map<String, String>) arguments.get(0);
                return db.get(arguments.get(1));
            }
            return null;
        }
    });

    inBuilts.put("trim", new MoonCallable() {
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

    inBuilts.put("load", new MoonCallable() {
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

    inBuilts.put("import", new MoonCallable() {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            //TODO: Check for a ".mods" file in the current path. If it exists,
            //check to see what modules are in it... and be able to load them by name.
            /**
             * EG. Lets say there is a module called "Frank" installed in the moon_mods directory...
             * The user can use `import("Frank");` in their code... and it will auto take that file...
             * And run it, (It will run a `Frank();` as it is the file name...
             *
             * If a `.mods` file doesnt exist, or does but does not contain what the user wants to import... we will
             * check the standard library.
             *
             * Well... Thats the end goal...
             */

            /**
             * Another idea is... if there is an internet connection, could always check an online API if there is a
             * "web_std" of what they are looking for. If there is, this will allow usage of std's that
             */

            //TODO: Check to see if it is already imported... if so, dont import it again...

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

            return "Expected argument 1 to be a String";
        }
    });

    inBuilts.put("check", new MoonCallable() {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            return arguments.get(0).getClass().getSimpleName();
        }
    });
 }
}
