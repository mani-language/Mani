package com.mani.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void>{
    
    final Environment globals = new Environment();
    private final Map<Expr, Integer> locals = new HashMap<>();
    private Environment environment = globals;

    Interpreter() {
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

    public ManiFunction getFunction(String key) {
        return (ManiFunction) globals.get(key);
    }

    /**
     * This function is used to go through every single statement and execute it, one by one.
     * @param statements
     */
    void interpret(List<Stmt> statements) {
        try{
            for(Stmt statement : statements) {
                execute(statement);
            }
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

    void executeBlock(List<Stmt> statements, Environment environment) {
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
        environment.define(stmt.name.lexeme, function);
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
        if (value instanceof ManiInstance) {
            if (( (ManiInstance) value).hasShowFn()) {
                ((ManiInstance) value).runShowFn(this);
                return null;
            }
        }
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
            throw new RuntimeError(expr.paren, "Can only call functions and classes");
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
        } else if ((object instanceof ArrayList || object instanceof HashMap) && expr.name.lexeme.equalsIgnoreCase("at")) {
            ManiCallable mc = Inbuilt.inBuilts.get(expr.name.lexeme);
            ManiCallableInternal mci = (ManiCallableInternal) mc;
            mci.setItem(object);
            return (ManiCallable) mci;
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
