package com.mani.lang;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Std {

    public static boolean hasRun = false;
    public static Map<String, String> std = new HashMap<>();
    public static List<String> loaded = new ArrayList<>();

    public static void Load() {
        File folder = new File(System.getProperty( "user.home" ) + "/mani");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name =  file.getName();
                std.put(name.substring(0, name.lastIndexOf(".")), System.getProperty("user.home") + "/mani/" + name);
            }
        }

        hasRun = true;
    }

    public static String find(String name) {
        if (name.contains(".")) name = name.substring(0, name.lastIndexOf("."));
        if (!loaded.contains(name)){
            loaded.add(name);
            if (std.containsKey(name)) {
                return std.get(name);
            } else {
                return "-1";
            }
        }
        return "-2";
    }

    public static int DoubleToInt(double item) {
        return new Double(item).intValue();
    }

    public static Double intToDouble(int item) {
        return new Double(item);
    }

    public static String loadFromUrl(Interpreter interpreter, String f) {
        Lexer lexer = new Lexer(f.toString());
        List<Token> tokens = lexer.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);
        interpreter.interpret(statements);
        return "Should have worked!";
    }

    public static String loadFile(Interpreter interpreter, String f) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(f));
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

}
