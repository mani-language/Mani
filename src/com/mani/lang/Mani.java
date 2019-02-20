package com.mani.lang;

import java.io.BufferedReader;
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

    private static final Interpreter interpreter = new Interpreter();
    public static void main(String[] args) {

            hasInternet = checkInternet();

            if(args.length > 1) {
                System.out.println("Usage mani [Script.mn]");
            } else if (args.length == 1) {
                runFile(args[0]);
            } else {
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
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    private static void runFile(String path) {
        if(path.endsWith(".mn")) {
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
            System.err.println("Mani scripts must end with '.mn'.");
        }
    }

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

    private static void run(String source) {
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

    static void error(int line, String message) {
        report(line, "", message);
    }

    static void error(Token token, String message) {
        if(token.type == TokenType.EOF) {
            report(token.line, "at end", message);
        } else {
            report(token.line, "at '" + token.lexeme + "' " , message);
        }
    }

    static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error " + where +" : " + message );
        hadError = true;
    }

    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    



}
