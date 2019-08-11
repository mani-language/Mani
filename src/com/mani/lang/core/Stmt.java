/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.core;

import com.mani.lang.token.Token;

import java.util.List;

public abstract class Stmt {
  interface Visitor<R> {
    R visitBlockStmt(Block stmt);
    R visitClassStmt(Class stmt);
    R visitExpressionStmt(Expression stmt);
    R visitIfStmt(If stmt);
    R visitSwitchStmt(Switch stmt);
    R visitFunctionStmt(Function stmt);
    R visitPrintStmt(Print stmt);
    R visitReturnStmt(Return stmt);
    R visitVarStmt(Var stmt);
    R visitWhileStmt(While stmt);
    R visitBreakStmt(Break stmt);
    R visitForEachStmt(ForEach stmt);
    R visitForEachMapStmt(ForEachMap stmt);
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

  static class Switch extends Stmt {
    Switch(Expr condition, List<Object> literals, List<List<Stmt>> statements) {
      this.condition = condition;
      this.literals = literals;
      this.statements = statements;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitSwitchStmt(this);
    }

    final Expr condition;
    final List<Object> literals;
    final List<List<Stmt>> statements;
  }

  public static class Function extends Stmt {
    Function(Token name, List<Token> parameters, List<Stmt> body, Boolean isPrivate, Boolean isOld) {
      this.name = name;
      this.parameters = parameters;
      this.body = body;
      this.isPrivate = isPrivate;
      this.isDepreciated = isOld;
    }

    <R> R accept(Visitor<R> visitor) {
      return visitor.visitFunctionStmt(this);
    }

    public final Token name;
    public final List<Token> parameters;
    public final List<Stmt> body;
    final boolean isPrivate;
    final boolean isDepreciated;
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

  static class ForEachMap extends Stmt {
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

  abstract <R> R accept(Visitor<R> visitor);
}
