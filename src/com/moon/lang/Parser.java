//Parser for moonLang - A straight recursive descent paser.
package com.moon.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.moon.lang.TokenType.*;

class Parser {
    private static class ParserError extends RuntimeException{}
    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while(!isAtEnd()) {
            statements.add(declarations());
        }
        return statements;
    }

    private Expr expression() {
        return assignment();
    }
    
    private Expr assignment() {
        Expr expr = or();
        if(match(EQUAL) || match(VAR_ARROW)) {
            Token equals = previous();
            Expr value = assignment();
            if(expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable)expr).name;
                return new Expr.Assign(name, value);
            } else if (expr instanceof Expr.Get) {
                Expr.Get get = (Expr.Get) expr;
                return new Expr.Set(get.object, get.name, value);
            }
            error(equals, "Invalid assignment target");
        }

        return expr;
    }

    private Expr or() {
        Expr expr = and();
        while(match(OR)) {
            Token operator = previous();
            Expr right = and();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }
    
    private Expr and() {
        Expr expr = equality();
        while(match(AND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }
    
    private Stmt declarations() {
        try{
            if(match(CLASS)) return classDeclaration();
            if(match(FN)) return function("function");
            if(match(LET)) return varDeclaration();
            return statement();
        } catch (ParserError erorr) {
            synchnorize();
            return null;
        }
    }
    private Stmt classDeclaration() {
        Token name = consume(IDENTIFIER, "Expect class name.");
        Expr.Variable superclass = null;
        if(match(LESS)) {
            consume(IDENTIFIER, "Expect supeclass name.");
            superclass = new Expr.Variable(previous());
        }
        consume(LEFT_BRACE, "Expect '{' after class name.");
        List<Stmt.Function> methods = new ArrayList<>();
        while(!check(RIGHT_BRACE) && !isAtEnd()) {
            methods.add(function("method"));
        }
        consume(RIGHT_BRACE, "Expect '}' after class body.");
        return new Stmt.Class(name, superclass, methods);
    }
    private Stmt statement() {
        if(match(PRINT)) return printStatement();
        if(match(RETURN)) return returnStatement();
        if(match(BREAK)) return breakStatement();
        if(match(WHILE)) return whileStatemnt();
        if(match(FOR)) return forStatement();
        if(match(IF)) return ifStatement();
        if(match(LEFT_BRACE)) return new Stmt.Block(block());
        return expressionStatement();
    }

    private Stmt forStatement() {
        consume(LEFT_PAREN, "Expect '(' after  for.");
        Stmt initializer = null;
        if(match(SEMICOLON)) {
            initializer = null;
        } else if (match(LET)) {
            initializer = varDeclaration();
        } else {
            initializer = expressionStatement();
        }
        Expr condition = null;
        if(!check(SEMICOLON)) {
            condition = expression();
        } 
        consume(SEMICOLON, "Expect ';' after loop condition.");
        Expr increment = null;
        if(!check(RIGHT_PAREN)) {
            increment = expression();
        }
        consume(RIGHT_PAREN, "Expect ')' after for clauses.");
        Stmt body = statement();
        if(increment != null) {
            body = new Stmt.Block(Arrays.asList(
                body,
                new Stmt.Expression(increment)
            ));
        }
        if(condition == null) condition = new Expr.Literal(true);
        body = new Stmt.While(condition, body);
        if(initializer != null) {
            body = new Stmt.Block(Arrays.asList(initializer, body));
        }
        return body;
    }

    private Stmt ifStatement() {
        consume(LEFT_PAREN, "Expect '(' after if.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after if condition");
        Stmt thenBrach = statement();
        Stmt elseBranch = null;
        if(match(ELSE)) {
            elseBranch = statement();
        }
        return new Stmt.If(condition, thenBrach, elseBranch);
    }

    private Stmt printStatement() {
        Expr value =  expression();
        consume(SEMICOLON, "Expect ';' after value");
        return new Stmt.Print(value);
    }

    private Stmt returnStatement() {
        Token keyword = previous();
        Expr value = null;
        if(!check(SEMICOLON)) {
            value = expression();
        }
        consume(SEMICOLON, "Expect ';' after return.");
        return new Stmt.Return(keyword, value);
    }
   private Stmt breakStatement() {
   	Token keyword = previous();
        consume(SEMICOLON, "Expect ';' after break.");
        return new Stmt.Break(keyword);
    } 
    private Stmt varDeclaration() {
        Token name = consume(IDENTIFIER, "Expect a variable name.");
        Expr initializer = null;
        if(match(EQUAL) || match(VAR_ARROW)) {
            initializer = expression();
        }
        consume(SEMICOLON, "Expect ';' after variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    private Stmt whileStatemnt() {
        consume(LEFT_PAREN, "Expect '(' after while.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after while condition.");
        Stmt body = statement();
        return new Stmt.While(condition, body);
    }

    private Stmt expressionStatement() {
        Expr expr = expression();
        consume(SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(expr);
    }

    private Stmt.Function function(String kind) {
        Token name = consume(IDENTIFIER, "Expect " + kind + " name.");
        consume(LEFT_PAREN, "Expect '(' after " + kind + " name.");
        List<Token> parameters = new ArrayList<>();
        if(!check(RIGHT_PAREN)) {
            do {
                if(parameters.size() >= 32) {
                    error(peek(), "Cannot have more than 32 parameters.");
                }
                parameters.add(consume(IDENTIFIER, "Expect parameter name."));
            } while(match(COMMA));
        }
        consume(RIGHT_PAREN, "Expect ')' after parameters");
        consume(LEFT_BRACE, "Expect '{' before " + kind + " body.");
        List<Stmt> body = block();
        return new Stmt.Function(name, parameters, body);
    } 

    private List<Stmt> block() {
        List<Stmt> statements = new ArrayList<>();
        while(!check(RIGHT_BRACE) && !isAtEnd()) {
            statements.add(declarations());
        }
        consume(RIGHT_BRACE, "Expect '}' after block.");
        return statements;
    }
    //Expressions
    private Expr equality() {
        Expr expr = comparision();
        while(match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparision();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }
    
    private Expr comparision() {
        Expr expr  = addition();
        while(match(GREATER, LESS, GREATER_EQUAL, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = addition();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }
    private Expr addition() {
        Expr expr = multiplication();
        while(match(PLUS, MINUS)) {
            Token operator = previous();
            Expr right = multiplication();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr multiplication() {
        Expr expr = unary();
        while(match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }
    private Expr unary() {
        if(match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }
        return call();
    }
    private Expr call() {
        Expr expr = primary();
        while(true) {
            if(match(LEFT_PAREN)) {
                expr = finishCall(expr);
            } else if (match(DOT)) {
                Token name = consume(IDENTIFIER, "Expect property name after '.' .");
                expr = new Expr.Get(expr, name);
            } else {
                break;
            }
        }
        return expr;
    }

    private Expr finishCall(Expr callee) {
        List<Expr> arguments = new ArrayList<>();
        if(!check(RIGHT_PAREN)) {
            do {
                if(arguments.size() >= 32) {
                    error(peek(), "Cannot have more than 32 arguments.");
                }
                arguments.add(expression());
            } while(match(COMMA));
        }
        Token paren = consume(RIGHT_PAREN, "Expect ')' after arguments.");
        return new Expr.Call(callee, paren, arguments);
    }

    private Expr primary() {
        if(match(FALSE)) return new Expr.Literal(false);
        if(match(TRUE)) return new Expr.Literal(true);
        if(match(NIL)) return new Expr.Literal(null);

        if(match(SUPER)) {
            Token keyword = previous();
            consume(DOT, "Expect '.' after super.");
            Token method = consume(IDENTIFIER, "Expect superclass method name.");
            return new Expr.Super(keyword, method);
        }
        if(match(THIS)) return new Expr.This(previous());
        if(match(IDENTIFIER)) return new Expr.Variable(previous());
        if(match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }
        if(match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }
        throw error(peek(), "Expect expression.");
    }
    //UTILS
    private Token consume(TokenType type, String message) {
        if(check(type)) return advance();
        throw error(peek(), message);
    }

    private ParserError error(Token token, String message) {
        Moon.error(token, message);
        return new ParserError();
    }

    private void synchnorize() {
        advance();
        while(!isAtEnd()) {
            if(previous().type == SEMICOLON) return;
            switch( peek().type ) {
                case CLASS:
                case FN:
                case WHILE:
                case FOR:
                case LET:
                case IF:
                case PRINT:
                case RETURN:
                case BREAK:
                return;
            }
            advance();
        }
    }
    
    private boolean match(TokenType... types) {
        for(TokenType type : types) {
            if(check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }
    private boolean check(TokenType type) {
        if(isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if(! isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }
    private Token previous() {
        return tokens.get(current - 1);
    }
    private Token next() {
        return tokens.get(current + 1);
    }
}
