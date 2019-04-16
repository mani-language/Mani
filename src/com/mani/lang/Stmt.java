package com.mani.lang;

import java.util.List;

abstract class Stmt {
  interface Visitor<R> {
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
  }
  static class Block extends Stmt {
    Block(List<Stmt> statements) {
      this.statements = statements;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBlockStmt(this);
    }

    final List<Stmt> statements;
  }
  static class Class extends Stmt {
    Class(Token name, Expr.Variable superclass, List<Function> methods) {
      this.name = name;
      this.superclass = superclass;
      this.methods = methods;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitClassStmt(this);
    }

    final Token name;
    final Expr.Variable superclass;
    final List<Function> methods;
  }
  static class Expression extends Stmt {
    Expression(Expr expression) {
      this.expression = expression;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitExpressionStmt(this);
    }

    final Expr expression;
  }
  static class If extends Stmt {
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
  static class Function extends Stmt {
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
    final List<Token> parameters;
    final List<Stmt> body;
    final Boolean isprivate;
  }
  static class Print extends Stmt {
    Print(Expr expression) {
      this.expression = expression;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitPrintStmt(this);
    }

    final Expr expression;
  }
  static class Return extends Stmt {
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
  static class Break extends Stmt {
      Break(Token keyword){
        this.keyword = keyword;
      }
      <R> R accept(Visitor<R> visitor) {
          return visitor.visitBreakStmt(this);
      }
      final Token keyword;
   }
  static class Var extends Stmt {
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
  static class While extends Stmt {
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

  static class ForEach extends Stmt {
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

  abstract <R> R accept(Visitor<R> visitor);
}
