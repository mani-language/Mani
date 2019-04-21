package com.mani.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class Mani {
    public static boolean hadError = false;
    public static boolean hadRuntimeError = false;
    public static boolean hasInternet = false;
    public static boolean isStrictMode = false;
    public static boolean compiledMode = false;

    private static final Interpreter interpreter = new Interpreter();

    /**
     * The core of all cores. This is where the magic begins.
     *
     * Looks for internet, this will setup the STDLIB to either use
     * online version, or local if setup.
     *
     * Then checks args to see if we are running the REPL or processing a file.g
     * @param args
     */
    public static void main(String[] args) {

            hasInternet = checkInternet();

            if(args.length > 1) {
                System.out.println("Usage mani [Script.mni]");
            } else if (args.length == 1) {
                runFile(args[0]);
            } else if (!compiledMode) {
                runPrompt();
            }

    }

    /**
     * Used to figure out if we are connected to the internet or not.
     *
     * @return internetStatus (boolean)
     */
    private static boolean checkInternet() {
        try {
            final URL url = new URL("http://www.github.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Used to load a file into its bytes, before processing.
     * @param path
     */
    private static void runFile(String path) {
        if(path.endsWith(".mni")) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(path));
                run(new String(bytes, Charset.defaultCharset()));
                if(hadError) System.exit(65);
            } catch(NoSuchFileException e) {
                System.out.println(path + ": File not Found");
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("Mani scripts must end with '.mni'.");
        }
    }

    /**
     * This is our REPL. It is basic, so please be nice.
     */
    private static void runPrompt() {
        System.out.println("The \u001B[36mMani\033[0m Programming Language");
        try{
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);

            for(;;) {
                System.out.print(">> ");
                run(reader.readLine());
                hadError = false;
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Used for processing files.
     * Takes the file and runs it through the following steps.
     * - Lexer
     * - Lexer verify
     * - Parser
     * - Run statements.
     * - Resolver
     * - Run statements.
     * - Interpreter
     * @param source
     */
    public static void run(String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        if(hadError) return;
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);
        if(hadError) return;
        interpreter.interpret(statements);

    }

    public static boolean fileExists(String fName) {
        File f;
        if (compiledMode) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            f = new File(classLoader.getResource(fName).getFile());
        } else {
            f = new File(fName);
        }

        return f.exists();
    }

    public static File internalFile(String fName) {
        //File f = new File((String)res);
        File f;
        if (compiledMode) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //InputStream inputStream = classLoader.getResourceAsStream(fName);
            f = new File(classLoader.getResource(fName).getFile());
        } else {
            f = new File(fName);
        }

        return f;
    }


    /**
     * Used for reporting an error, with the line and message.
     * @param line
     * @param message
     */
    static void error(int line, String message) {
        report(line, "", message);
    }

    /**
     * Used for reporting an error, with token and message.
     * @param token
     * @param message
     */
    static void error(Token token, String message) {
        if(token.type == TokenType.EOF) {
            report(token.line, "at end", message);
        } else {
            report(token.line, "at '" + token.lexeme + "' " , message);
        }
    }

    /**
     * Used by `error` to print the message to the console.
     * @param line
     * @param where
     * @param message
     */
    static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error " + where +" : " + message );
        hadError = true;
    }

    /**
     * Used for handling runtime errors, and printing them
     * in the console.
     * @param error
     */
    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    



}
