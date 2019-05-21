package com.mani.lang.core;

import com.mani.lang.main.Mani;
import com.mani.lang.token.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Resolver implements Expr.Visitor<Void>, Stmt.Visitor<Void> {
    private final Interpreter interpreter;
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();
    private FunctionType currentFunction = FunctionType.NONE;
    private ClassType currentClass = ClassType.NONE;
    private Boolean isInLoop = false;
    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
    enum FunctionType {
        NONE,
        FUNCTION,
        INITIALIZER,
        METHOD
    }
    enum ClassType {
        NONE,
        CLASS,
        SUBCLASS
    }

    public void resolve(List<Stmt> statements) {
        for(Stmt statement : statements) {
            resolve(statement);
        }
    }

    private void resolve(Stmt stmt) {
        stmt.accept(this);
    }

    private void resolve(Expr expr) {
        expr.accept(this);
    }

    private void beginScope() {
        scopes.push(new HashMap<String, Boolean>());
    }

    private void endScope() {
        scopes.pop();
    }

    private void declare(Token name) {
        if(scopes.isEmpty()) return;
        Map<String, Boolean> scope = scopes.peek();
        if(scope.containsKey(name.lexeme)) {
            Mani.error(name, "Variable already exists in this scope.");
        }
        scope.put(name.lexeme, false);
    }

    private void define(Token name) {
        if(scopes.isEmpty()) return;
        scopes.peek().put(name.lexeme, true);
    }

    private void resolveLocal(Expr expr, Token name) {
        for(int i = scopes.size()  - 1 ; i >= 0 ; i--) {
            if(scopes.get(i).containsKey(name.lexeme)) {
                interpreter.resolve(expr, scopes.size() - 1 - i);
                return;
            }
        }
    }

    private void resolveFunction (Stmt.Function function , FunctionType type)  {
        FunctionType enclosingFunction = currentFunction;
        currentFunction  = type;
        beginScope();
        for(Token param : function.parameters) {
            declare(param);
            define(param);
        }
        resolve(function.body);
        endScope();
        currentFunction = enclosingFunction;
    }

    @Override
    public Void visitClassStmt(Stmt.Class stmt) {
        ClassType enclosingClass = currentClass;
        currentClass = ClassType.CLASS;
        declare(stmt.name);
        if(stmt.superclass != null ) {
            resolve(stmt.superclass);
        }
        define(stmt.name);
        if(stmt.superclass != null) {
            currentClass = ClassType.SUBCLASS;
            beginScope();
            scopes.peek().put("super", true);
        }
        beginScope();
        scopes.peek().put("this", true);
        for(Stmt.Function method : stmt.methods) {
            FunctionType declaration = FunctionType.METHOD;
            if(method.name.lexeme.equals(stmt.name.lexeme)) {
                declaration = FunctionType.INITIALIZER;
            }
            resolveFunction(method, declaration);
        }
        endScope();
        if(stmt.superclass != null) endScope();
        currentClass = enclosingClass;
        return null;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        beginScope();
        resolve(stmt.statements);
        endScope();
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        resolve(stmt.expression);
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        resolve(stmt.condition);
        resolve(stmt.thenBranch);
        if(stmt.elseBranch != null) resolve(stmt.elseBranch);
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        resolve(stmt.expression);
        return null;
    }

    @Override
    public Void visitReturnStmt(Stmt.Return stmt) {
        if(currentFunction == FunctionType.NONE) {
            Mani.error(stmt.keyword, "Cannot return from top-level code.");
        }
        if(stmt.value != null) {
            if(currentFunction == FunctionType.INITIALIZER) {
                Mani.error(stmt.keyword, "Cannot return value from an initializer.");
            }
            resolve(stmt.value);
        }
        return null;
    }

    @Override
    public Void visitFunctionStmt(Stmt.Function stmt) {
        declare(stmt.name);
        define(stmt.name);
        resolveFunction(stmt, FunctionType.FUNCTION);
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        declare(stmt.name);
        if(stmt.initializer != null) {
            resolve(stmt.initializer);
        }
        define(stmt.name);
        return null;
    }

    @Override
    public Void visitVariableExpr(Expr.Variable expr) {
        if(!scopes.isEmpty() && scopes.peek().get(expr.name.lexeme) == Boolean.FALSE) {
            Mani.error(expr.name, "Cannot read local variable in its own initializer.");
        }
        resolveLocal(expr, expr.name);
        return null;
    }

    @Override
    public Void visitAssignExpr(Expr.Assign expr) {
        resolve(expr.value);
        resolveLocal(expr, expr.name);
        return null;
    }

    @Override
    public Void visitBinaryExpr(Expr.Binary expr) {
        resolve(expr.left);
        resolve(expr.right);
        return null;
    }

    @Override
    public Void visitCallExpr(Expr.Call expr) {
        resolve(expr.callee);
        for(Expr argument : expr.arguments) {
            resolve(argument);
        }
        return null;
    }

    @Override
    public Void visitGetExpr(Expr.Get expr) {
        resolve(expr.object);
        return null;
    }

    @Override
    public Void visitSetExpr(Expr.Set expr) {
        resolve(expr.value);
        resolve(expr.object);
        return null;
    }

    @Override
    public Void visitSuperExpr(Expr.Super expr) {
        if(currentClass == ClassType.NONE) {
            Mani.error(expr.keyword, "Cannot use super outside of a class.");
        } else if (currentClass != ClassType.SUBCLASS) {
            Mani.error(expr.keyword, "Cannot use super in a class with no superclass.");
        }
        resolveLocal(expr, expr.keyword);
        return null;
    }
    @Override
    public Void visitGroupingExpr(Expr.Grouping expr) {
        resolve(expr.expression);
        return null;
    }

    @Override
    public Void visitLiteralExpr(Expr.Literal expr) {
        return null;
    }

    @Override
    public Void visitLogicalExpr(Expr.Logical expr) {
        resolve(expr.left);
        resolve(expr.right);
        return null;
    }

    @Override
    public Void visitUnaryExpr(Expr.Unary expr) {
        resolve(expr.right);
        return null;
    }

    @Override
    public  Void visitThisExpr(Expr.This expr) {
        if(currentClass == ClassType.NONE) {
            Mani.error(expr.keyword, "Cannot use this outside of a class.");
            return null;
        }
        resolveLocal(expr, expr.keyword);
        return null;
    }
    
    @Override
    public Void visitBreakStmt(Stmt.Break stmt) {
        if(!isInLoop) {
            Mani.error(stmt.keyword, "Cannot use break outside of a loop.");
        }
        return null;
    }

    @Override
    public Void visitForEachStmt(Stmt.ForEach stmt) {
        Boolean enclosignLoop = isInLoop;
        isInLoop = true;
        resolve(stmt.container);
        resolve(stmt.body);
        isInLoop = enclosignLoop;
        return null;
    }

    @Override
    public Void visitForEachMapStmt(Stmt.ForEachMap stmt) {
        Boolean enclosingLoop = isInLoop;
        isInLoop = true;
        resolve(stmt.container);
        resolve(stmt.body);
        isInLoop = enclosingLoop;
        return null;
    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        Boolean enclosignLoop = isInLoop;
        isInLoop = true;
        resolve(stmt.condition);
        resolve(stmt.body);
        isInLoop = enclosignLoop;
        return null;
    }
    
    @Override
    public Void visitCopyExpr(Expr.Copy expr) {
        resolveLocal(expr, expr.name);
        return null;
    }

    @Override
    public Void visitArrayExpr(Expr.Array expr) {
        for(Expr e : expr.elements) {
            resolve(e);
        }
        return null;
    }

    @Override
    public Void visitMapExpr(Expr.Map expr) {
        for (Expr e : expr.elements.keySet()) {
            resolve(e);
        }
        for (Expr e : expr.elements.values()) {
            resolve(e);
        }
        return null;
    }

    @Override
    public Void visitLoadExpr(Expr.Load expr) {
        resolve(expr.toLoad);
        return null;
    }

    @Override
    public Void visitImportExpr(Expr.Import expr) {
        resolve(expr.toImport);
        return null;
    }

}