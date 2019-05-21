package com.mani.lang;

import com.mani.lang.Token.Token;

import java.util.List;

public abstract class Stmt {
  public interface Visitor<R> {
    R visitBlockStmt(Block stmt);
    R visitClassStmt(Class stmt);
    R visitExpressionStmt(Expression stmt);
    R visitIfStmt(If stmt);
    R visitFunctionStmt(Function stmt);
    R visitPrintStmt(Print stmt);
    R visitReturnStmt(Return stmt);
    R visitVarStmt(Var stmt);
    R visitWhileStmt(While stmt);
    R visitBreakStmt(Break stmt);
    R visitForEachStmt(ForEach stmt);
    R visitForEachMapStmt(ForEachMap stmt);
  }
  public static class Block extends Stmt {
    Block(List<Stmt> statements) {
      this.statements = statements;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBlockStmt(this);
    }

    public final List<Stmt> statements;
  }
  public static class Class extends Stmt {
    Class(Token name, Expr.Variable superclass, List<Function> methods) {
      this.name = name;
      this.superclass = superclass;
      this.methods = methods;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitClassStmt(this);
    }

    public final Token name;
    public final Expr.Variable superclass;
    public final List<Function> methods;
  }
  public static class Expression extends Stmt {
    Expression(Expr expression) {
      this.expression = expression;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitExpressionStmt(this);
    }

    final Expr expression;
  }
  public static class If extends Stmt {
    If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
      this.condition = condition;
      this.thenBranch = thenBranch;
      this.elseBranch = elseBranch;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitIfStmt(this);
    }

    final Expr condition;
    final Stmt thenBranch;
    final Stmt elseBranch;
  }
  public static class Function extends Stmt {
    Function(Token name, List<Token> parameters, List<Stmt> body, Boolean isprivate) {
      this.name = name;
      this.parameters = parameters;
      this.body = body;
      this.isprivate = isprivate;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitFunctionStmt(this);
    }

    final Token name;
    public final List<Token> parameters;
    final List<Stmt> body;
    final Boolean isprivate;
  }
  public static class Print extends Stmt {
    Print(Expr expression) {
      this.expression = expression;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitPrintStmt(this);
    }

    final Expr expression;
  }
  public static class Return extends Stmt {
    Return(Token keyword, Expr value) {
      this.keyword = keyword;
      this.value = value;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitReturnStmt(this);
    }

    final Token keyword;
    final Expr value;
  }
  public static class Break extends Stmt {
      Break(Token keyword){
        this.keyword = keyword;
      }
      <R> R accept(Visitor<R> visitor) {
          return visitor.visitBreakStmt(this);
      }
      final Token keyword;
   }
  public static class Var extends Stmt {
    Var(Token name, Expr initializer) {
      this.name = name;
      this.initializer = initializer;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitVarStmt(this);
    }

    final Token name;
    final Expr initializer;
  }
  public static class While extends Stmt {
    While(Expr condition, Stmt body) {
      this.condition = condition;
      this.body = body;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitWhileStmt(this);
    }

    final Expr condition;
    final Stmt body;
  }

  public static class ForEach extends Stmt {
      ForEach(Token id, Stmt block, Expr container) {
        this.id = id;
        this.body = block;
        this.container = container;
      }

      <R> R accept(Visitor<R> visitor) { return visitor.visitForEachStmt(this); }

      final Token id;
      final Stmt body;
      final Expr container;
  }

  public static class ForEachMap extends Stmt {
    ForEachMap(Token key, Token val, Stmt block, Expr container) {
      this.key = key;
      this.val = val;
      this.body = block;
      this.container = container;
    }

    <R> R accept(Visitor<R> visitor) { return visitor.visitForEachMapStmt(this); }

    final Token key;
    final Token val;
    final Stmt body;
    final Expr container;
  }

  public abstract <R> R accept(Visitor<R> visitor);
}
