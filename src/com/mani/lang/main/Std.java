package com.mani.lang.main;

import com.mani.lang.core.Interpreter;
import com.mani.lang.core.Lexer;
import com.mani.lang.core.Parser;
import com.mani.lang.core.Resolver;
import com.mani.lang.core.Stmt;
import com.mani.lang.token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Std {

    public static boolean hasRun = false; // This if for checking to see if we have search for STDLIBS
    public static Map<String, String> std = new HashMap<>(); // This is the STDLIB paths
    public static List<String> loaded = new ArrayList<>(); // This is the files used by STDLib
    public static List<String> loadedFiles = new ArrayList<>(); // This is the files used in "load("blah");"


    public static void Load() {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(Mani.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> e = jarFile.entries();    // Gets the content of the jarfile
        while (e.hasMoreElements()) {
            JarEntry entry = e.nextElement();
            String name = entry.getName();
            if (!name.contains("stdlib") || !name.contains(".mni")) { // If file is .mni script and is stdlib then add it to list of stlib files
                continue;
            }
            std.put(name.substring(name.lastIndexOf('/') + 1, name.lastIndexOf('.')), name);
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

    public static Double makeDouble(int item) {
        return new Double(item);
    }

    public static String loadFromUrl(Interpreter interpreter, String f) {
        Lexer lexer = new Lexer(f.toString(), f);
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
            ClassLoader classLoader = Mani.class.getClassLoader();      // Loads stdlib from directory within .jar file
            InputStream stream = classLoader.getResourceAsStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder inputString = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                inputString.append(line).append("\n");
                line = reader.readLine();
            }
            Lexer lexer = new Lexer(inputString.toString(), f);
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
