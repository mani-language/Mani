package com.mani.lang.core;

import com.mani.lang.exceptions.*;

import com.mani.lang.enviroment.Environment;
import com.mani.lang.enviroment.Inbuilt;
import com.mani.lang.Modules.Module;
import com.mani.lang.exceptions.RuntimeError;
import com.mani.lang.main.Mani;
import com.mani.lang.main.Std;
import com.mani.lang.token.Token;
import com.mani.lang.token.TokenType;
import com.mani.lang.local.Locals;
import com.mani.lang.domain.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {

    final Environment globals = new Environment();
    private final Map<Expr, Integer> locals = new HashMap<>();
    private Environment environment = globals;

    public Interpreter() {
        /**
         * Loading the inBuilts hashMap, which is the built in functions / apis for the language.
         * We are just defining them in the enviroment
         */
        if(! Inbuilt.inBuilts.isEmpty()) {
            for(String key : Inbuilt.inBuilts.keySet()) {
                globals.define(key, Inbuilt.inBuilts.get(key));
            }
        }
    }

    public void addSTD(String key, ManiCallable mani) {
        globals.define(key, mani);
    }

    public void define(String key, Object val) {
        globals.define(key, val);
    }

    public void wipeMemory() { this.globals.wipe();}

    public ManiFunction getFunction(String key) {
        return (ManiFunction) globals.get(key);
    }

    /**
     * This function is used to go through every single statement and execute it, one by one.
     * @param statements
     */
    public void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        }catch(GeneralError ge) {
            Mani.generalError(ge);
        } catch(RuntimeError error) {
            Mani.runtimeError(error);
        }
    }

    /**
     * This function converts whatever it is given, to a string to be used.
     * @param object
     * @return
     */
    private String stringfy(Object object) {
        if(object == null) return "nil";
        if(object instanceof Double) {
            String text = object.toString();
            if(text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
        return text;
        }
        if (object instanceof ManiInstance) {
            if (((ManiInstance)object).hasShowFn()) {
                return (((ManiInstance) object).runShowFn(this));
            }
        }
        return object.toString();
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
        Object left = evaluate(expr.left);
        if(expr.operator.type == TokenType.OR) {
            if(isTruthy(left)) return left;
        } else {
            if(!isTruthy(left)) return left;
        }
        return evaluate(expr.right);
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    public Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    public void execute(Stmt stmt) {
         stmt.accept(this);
    }

    void resolve(Expr expr, int depth) {
        locals.put(expr, depth);
    }

    public void executeBlock(List<Stmt> statements, Environment environment) {
        Environment previous = this.environment;
        try{
            this.environment = environment;
            for(Stmt statement : statements) {
                execute(statement);
            }
        } finally {
                this.environment = previous;
            }
    }

    @Override
    public Void visitClassStmt(Stmt.Class stmt) {
        Object superclass = null;
        if(stmt.superclass != null) {
           superclass =  evaluate(stmt.superclass);
           if(!(superclass instanceof ManiClass)) {
               throw new RuntimeError(stmt.superclass.name, "Superclass must be a class.");
           }
        }

        environment.define(stmt.name.lexeme, null);
        if(stmt.superclass != null) {
            environment = new Environment(environment);
            environment.define("super", superclass);
        }

        Map<String, ManiFunction> methods = new HashMap<>();
        for(Stmt.Function method : stmt.methods) {
            ManiFunction function = new ManiFunction(method, environment, ((String)method.name.lexeme).equals((String)stmt.name.lexeme), (Boolean) method.isprivate);
            methods.put(method.name.lexeme, function);
        }

        ManiClass klass = new ManiClass(stmt.name.lexeme, (ManiClass) superclass, methods);
        if(superclass != null) {
            environment = environment.enclosing;
        }
        environment.assign(stmt.name, klass);
        return null;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        executeBlock(stmt.statements, new Environment(environment));
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitFunctionStmt(Stmt.Function stmt) {
        ManiFunction function = new ManiFunction(stmt, environment, false, stmt.isprivate);
        // Check if the function exists...
        if (environment.contains(stmt.name)) {
            environment.defineMultiple(stmt.name.lexeme, function);
        } else {
            environment.define(stmt.name.lexeme, function);
        }
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        if(isTruthy(evaluate(stmt.condition))) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringfy(value));
        return null;
    }

    @Override
    public Void visitReturnStmt(Stmt.Return stmt) {
        Object value = null;
        if(stmt.value != null) value = evaluate(stmt.value);
        throw new Return(value);
    }

    @Override
    public Void visitBreakStmt(Stmt.Break stmt) {
        throw new Return();
    }

    @Override
    public Void visitForEachStmt(Stmt.ForEach stmt) {
        String result = Locals.getType(evaluate(stmt.container));
        assert result != null;
        switch (result) {
            case "list":
                for (Object item : (List) evaluate(stmt.container)) {
                    globals.assignForce(stmt.id, item);
                    execute(stmt.body);
                }
                globals.cleanupForce(stmt.id);
                return null;
            case "string":
                for (Object item : ((String) evaluate(stmt.container)).toCharArray()) {
                    globals.assignForce(stmt.id, item);
                    execute(stmt.body);
                }
                globals.cleanupForce(stmt.id);
                return null;
            case "number":
                for (Object item : ((String) evaluate(stmt.container)).toCharArray()) {
                    globals.assignForce(stmt.id, new Double((int) item));
                    execute(stmt.body);
                }
                globals.cleanupForce(stmt.id);
                return null;
        }

        throw new RuntimeException("Must be a String, Number or List");
    }

    @Override
    public Void visitForEachMapStmt(Stmt.ForEachMap stmt) {
        if (Locals.getType(evaluate(stmt.container)).equalsIgnoreCase("map")) {
            HashMap<Object, Object> db = (HashMap<Object, Object>) evaluate(stmt.container);
            for (Object item : db.keySet()) {
                globals.assignForce(stmt.key, item);
                globals.assignForce(stmt.val, db.get(item));
                execute(stmt.body);
            }
            globals.cleanupForce(stmt.key);
            globals.cleanupForce(stmt.val);
            return null;
        }
        throw new RuntimeException("Must be a map");
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if(stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }
        environment.define(stmt.name.lexeme, value);
        return null;
    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        while(isTruthy(evaluate(stmt.condition))) {
            try {
                execute(stmt.body);
            } catch (Return stop) {
                break;
            }
        }
        return null;
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = evaluate(expr.value);
        Integer distance = locals.get(expr);
        if(distance != null) {
            environment.assignAt(distance, expr.name, value);
        } else {
            globals.assign(expr.name, value);
        }
        return value;
    }

    @Override
    public Object visitCopyExpr(Expr.Copy expr) {
       Integer distance = locals.get(expr);
       if (distance != null) {
           environment.assignAt(distance, expr.name, globals.get(expr.from));
       } else {
           globals.assign(expr.name, globals.get(expr.from));
       }
       return globals.get(expr.from);
    }

    @Override
    public Object visitArrayExpr(Expr.Array expr) {
        List<Object> db = new ArrayList<>();
        for (Expr e : expr.elements) {
            db.add(evaluate(e));
        }
        return db;
    }

    @Override
    public Object visitMapExpr(Expr.Map expr) {
        HashMap<Object, Object> db = new HashMap<>();
        for (Expr e : expr.elements.keySet()) {
            db.put(evaluate(e), evaluate(expr.elements.get(e)));
        }
        return db;
    }

    @Override
    public Object visitLoadExpr(Expr.Load expr) {
        // This is where we need to actually load the file now, or API... depends if the file exists.
        Object res = evaluate(expr.toLoad);
        if (!(res instanceof String)) {
            System.err.println("Must be presented as a string");
            return null;
        }
        // If we have gotten this far, we should check for a file.

        if (Mani.fileExists((String) res)) {
            // This means we load this over the API.
            File f = Mani.internalFile((String) res);
            try {
                //byte[] bytes = Files.readAllBytes(Paths.get((String) res));
                byte[] bytes = Mani.readFileToByteArray(f);
                Lexer lexer = new Lexer(new String(bytes, Charset.defaultCharset()), f.getName());
                List<Token> tokens = lexer.scanTokens();
                Parser parser = new Parser(tokens);
                List<Stmt> statements = parser.parse();
                Resolver resolver = new Resolver(this);
                resolver.resolve(statements);
                this.interpret(statements);
            } catch (IOException e) {
                return e;
            }
        } else {
            try {
                // TODO: This is where we need work on issue #22
                final String moduleName = (String) res;
                final Module module = (Module) Class.forName("com.mani.lang.Modules." + moduleName + "." + moduleName).newInstance();
                module.init(this);
                if (module.hasExtensions()) {
                    Locals.add(module.extensions());
                }
            } catch (Exception e) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Object visitImportExpr(Expr.Import expr) {
        // This is where we need to import the library from the internet, or local. Depends if there is internet.
        Object result = evaluate(expr.toImport);
        if (!(result instanceof String)) {
            System.err.println(result.getClass().getSimpleName());
            System.err.println("Must be presented as a string");
            return null;
        }

        if (Mani.hasInternet) {
            // If there is internet, we will choose to use that STDLIB over the local...
            // simply due to the fact that it will be more up-to-date.
            if (Std.find((String) result).equalsIgnoreCase("-2")){
                // This means it has already been loaded.
                return "Already loaded!";
            } else {
                try {
                    URL url = new URL(Mani.getStdLibURL() + (String) result + ".mni");
                    Scanner s = new Scanner(url.openStream());
                    String final_file = "";
                    while(s.hasNextLine()){
                        final_file += s.nextLine() + "\n";
                    }
                    Std.loadFromUrl(this, final_file);
                    return final_file;
                }
                catch(IOException ex) {
                    String res = Std.find((String) result);
                    if (res.equalsIgnoreCase("-1")) {
                        System.err.println("No such library!");
                    } else {
                        return Std.loadFile(this, res);
                    }
                }
            }
        } else {
            // As there is no internet. We are going to have to try and use the local version
            // That is if the user actually has them installed...
            if (!Std.hasRun) {
                Std.Load();
            }
            String res = Std.find((String) result);
            if (res.equalsIgnoreCase("-1")) {
                System.err.println("No such library!");
            } else if (res.equalsIgnoreCase("-2")) {
                return "Already loaded!";
            } else {
                return Std.loadFile(this, res);
            }
        }
        return null;
    }

    @Override
    public Object visitNameSpaceExpr(Expr.Namespace expr) {
        String namespace = this.environment.defaultNamespace;
        if (expr.newNamespace != null) {
            namespace = String.valueOf(this.evaluate(expr.newNamespace));
        }
        this.environment.switchNamespace(namespace);
        return null;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);
        switch(expr.operator.type) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double) right;
        }
        return null;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return lookUpVariable(expr.name, expr);
    }

    private Object lookUpVariable(Token name, Expr expr) {
        Integer distance = locals.get(expr);
        if(distance != null) {
            return environment.getAt(distance, name.lexeme);
        } else {
            return globals.get(name);
        }
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if(operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void  checkNumberOperands(Token operator, Object left, Object right){
        if(left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Both operand must be numbers.");
    }

    private boolean isTruth(Object object) {
        if(object == null) return false;
        if(object instanceof Boolean) return (Boolean) object;
        return true;
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);
        switch (expr.operator.type) {
            case PERCENT:
                return (double) left % (double) right;
            case STAR_STAR:
                return new Double(Math.pow((double) left, (double) right));
            case MINUS:
                if (left instanceof ManiInstance && ((ManiInstance) left).hasFunction("minus")) {
                    List<Object> arguments = new ArrayList<>();
                    arguments.add(right);
                    return ((ManiFunction)((ManiInstance) left).get(new Token(TokenType.IDENTIFIER, "minus", "", 0))).call(this, arguments);
                }
                checkNumberOperands(expr.operator, left, right);
                return (double) left  - (double) right;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double) left / (double) right;
            case STAR:
                if(left instanceof Double && right instanceof Double){
                    return (double) left * (double) right;
                }
                if(left instanceof String && right instanceof Double) {
                    Double _repeat = (Double) right;
                    String s = (String) left;
                    Integer repeat = _repeat.intValue();
                    return  new String(new char[repeat]).replace("\0", s);
                }
                throw new RuntimeError(expr.operator, "Operands must be number and number or string and number.");
            case PLUS:
                if(left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if(left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }
                if(left instanceof String && right instanceof Double) {
                    right = stringfy(right);
                    return (String) left + (String) right;
                }
                if(left instanceof Double && right instanceof String) {
                    left = stringfy(left);
                    return (String) left + (String) right;
                }
                if(left instanceof String && right instanceof ManiInstance) {
                    right = stringfy(right);
                    return (String) left + (String) right;
                }
                if (left instanceof ManiInstance && right instanceof String) {
                    left = stringfy(left);
                    return (String) left + (String) right;
                }
                if (left instanceof ManiInstance && ((ManiInstance) left).hasFunction("add")) {
                    List<Object> arguments = new ArrayList<>();
                    arguments.add(right);
                    return ((ManiFunction)((ManiInstance) left).get(new Token(TokenType.IDENTIFIER, "add", "", 0))).call(this, arguments);
                }
                throw new RuntimeError(expr.operator, "Operands must be numbers or strings");
            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double) left > (double) right;
            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left < (double) right;
            case GREATER_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left >= (double) right;
            case LESS_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left <= (double) right;
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case IS:
                return Locals.getType(left).equalsIgnoreCase(right.toString());
            case AS:
                if (Locals.canConvert(left, right)) {
                    return Locals.convert(left, right.toString());
                }
                System.err.println("Cannot convert to that type!");
                return null;
        }
        return null;
    }

     @Override
    public Object visitCallExpr(Expr.Call expr) {
        Object callee = evaluate(expr.callee);
        List<Object> arguments = new ArrayList<>();
        for(Expr argument : expr.arguments) {
            arguments.add(evaluate(argument));
        }

        if(! (callee instanceof ManiCallable)) {
            // Used by function over loader.
            if (!(callee instanceof List && ((List<Object>) callee).get(0) instanceof ManiCallable)) {
                throw new RuntimeError(expr.paren, "Can only call functions and classes");
            }
        }
        // Used for overloading functions
        if (callee instanceof List) {
            for (Object callable : (List<Object>) callee) {
                ManiCallable function = (ManiCallable) callable;
                if (function.arity() == -1 || arguments.size() == function.arity()) {
                    return function.call(this, arguments);
                }
            }
            throw new RuntimeError(expr.paren, "No function like that found!");
        }
        ManiCallable function = (ManiCallable)callee;
        if(function.arity() != -1 && arguments.size() != function.arity()) {
            throw new RuntimeError(expr.paren, "Expected "+ function.arity() +" argument(s) but got "+ arguments.size() + ".");
        }
        return function.call(this, arguments);
    }

    @Override
    public Object visitGetExpr(Expr.Get expr) {
        Object object = evaluate(expr.object);
        if(object instanceof ManiInstance) {
            if (((ManiInstance)object).get(expr.name) instanceof ManiFunction) {
                ManiFunction holder = (ManiFunction) ((ManiInstance)object).get(expr.name);
                if (holder.isPrivate() && !expr.fromThis) {
                    throw new RuntimeError(expr.name, "Sorry, this is a private function!");
                }
            }
            return ((ManiInstance)object).get(expr.name);
        } else if (Locals.canWorkWith(object, expr.name.lexeme)) {
            ManiCallable mc = Locals.getFunction(object, expr.name.lexeme);
            ManiCallableInternal mci = (ManiCallableInternal) mc;
            mci.setItem(object);
            return (ManiCallable) mci;
        }
        //TODO: Create a whole method, that checks for methods built into the system. So then
        // we dont have to keep creating STANDARD LIBRARIES for no reason.

       if (Locals.areFunctions(object)) {
           throw new RuntimeError(expr.name, "Error: '" + expr.name.lexeme + "'" + " is not a known extension.");
       }

        throw new RuntimeError(expr.name, "Only instances have properties.");
    }
    @Override
    public Object visitSetExpr(Expr.Set expr) {
        Object object = evaluate(expr.object);

        if(!(object instanceof ManiInstance)) {
            throw new RuntimeError(expr.name, "Only instances have fields.");
        }
        Object value = evaluate(expr.value);
        ((ManiInstance)object).set(expr.name, value);
        return value;
    }

    @Override
    public Object visitSuperExpr(Expr.Super expr) {
        int distance = locals.get(expr);
        ManiClass superclass = (ManiClass) environment.getAt(distance, "super");
        ManiInstance object = (ManiInstance) environment.getAt(distance - 1, "this");
        ManiFunction method = superclass.findMethod(object, expr.method == null ? superclass.getName() : expr.method.lexeme);
        if (method.isPrivate()) {
            throw new RuntimeError(expr.method, "Sorry, this is a private function!");
        }
        if(method == null) {
            throw new RuntimeError(expr.method == null ? new Token(TokenType.IDENTIFIER, superclass.getName(), 0, 0) : expr.method, "Undefined property '" + (expr.method == null ? superclass.getName() : expr.method.lexeme) +"'.");
        }
        return method;
    }

    @Override
    public Object visitThisExpr(Expr.This expr) {
        return lookUpVariable(expr.keyword, expr);
    }
    private boolean isEqual(Object left, Object right) {
        if(left == null && right == null) return true;
        if(left == null || right == null) return false;
        return left.equals(right);
    }
    private boolean isTruthy(Object object) {
        if(object == null) return false;
        if(object instanceof Boolean) return (Boolean) object;
        return true;
    }

}
