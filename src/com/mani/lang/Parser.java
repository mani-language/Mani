//Parser for moonLang - A straight recursive descent paser.
package com.mani.lang;

import java.util.*;

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
            if (peek().type == TokenType.STRICT) {
                advance();
                consume(TokenType.SEMICOLON, "Need ';' after STRICT", true);
                advance();
                Mani.isStrictMode = true;
            } else {
                statements.add(declarations());
            }
        }
        return statements;
    }

    private Expr expression() {
        return assignment();
    }
    
    private Expr assignment() {

        Expr expr = or();

        if (match(TokenType.ASSIGN_ARROW)) {
            Expr value = assignment();
            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable)expr).name;
                Token now = previous();
                return new Expr.Copy(now, name);
            }
        }

        if(match(TokenType.EQUAL) || match(TokenType.VAR_ARROW) || match(TokenType.PERCENT_ASSIGN) || match(TokenType.MINUS_ASSIGN) || match(TokenType.PLUS_ASSIGN) || match(TokenType.STAR_ASSIGN) || match(TokenType.SLASH_ASSIGN)) {
            Token op = previous();
            TokenType op_type = op.type;

            final boolean is_assign_op = op_type == TokenType.MINUS_ASSIGN
                    || op_type == TokenType.PLUS_ASSIGN
                    || op_type == TokenType.STAR_ASSIGN
                    || op_type == TokenType.SLASH_ASSIGN
                    || op_type == TokenType.PERCENT_ASSIGN;

            TokenType bin_op_type = null;
            switch (op_type) {
                case PLUS_ASSIGN:  bin_op_type = TokenType.PLUS; break;
                case MINUS_ASSIGN: bin_op_type = TokenType.MINUS; break;
                case STAR_ASSIGN: bin_op_type = TokenType.STAR; break;
                case SLASH_ASSIGN: bin_op_type = TokenType.SLASH; break;
                case PERCENT_ASSIGN: bin_op_type = TokenType.PERCENT; break;
            }

            Expr value = assignment();
            if(expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable)expr).name;

                if (is_assign_op) {
                    Token bin_op = new Token(bin_op_type, op.lexeme, op.literal, op.line);

                    Expr.Variable lhs_var = new Expr.Variable(name);
                    System.err.println(lhs_var.name + " " + bin_op.type + " " + value);
                    Expr.Binary bin_expr = new Expr.Binary(lhs_var, bin_op, value);
                    return new Expr.Assign(name, bin_expr);
                }
                return new Expr.Assign(name, value);
            } else if (expr instanceof Expr.Get) {
                Expr.Get get = (Expr.Get) expr;

                if (is_assign_op) {
                    Token bin_op = new Token(bin_op_type, op.lexeme, op.literal, op.line);

                    Expr.Binary bin_expr = new Expr.Binary(get, bin_op, value);
                    return new Expr.Set(get.object, get.name, bin_expr);
                }

                return new Expr.Set(get.object, get.name, value);
            }
            error(op, "Invalid assignment target");
        }

        return expr;
    }

    private Expr or() {
        Expr expr = and();
        while(match(TokenType.OR)) {
            Token operator = previous();
            Expr right = and();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }
    
    private Expr and() {
        Expr expr = equality();
        while(match(TokenType.AND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }
    
    private Stmt declarations() {
        try{
            if(match(TokenType.INTERNAL)) return function("function");
            if(match(TokenType.CLASS)) return classDeclaration();
            if(match(TokenType.FN)) return function("function");
            if(match(TokenType.LET)) return varDeclaration();
            return statement();
        } catch (ParserError erorr) {
            synchnorize();
            return null;
        }
    }
    private Stmt classDeclaration() {
        Token name = consume(TokenType.IDENTIFIER, "Expect class name.");
        Expr.Variable superclass = null;
        if(match(TokenType.LESS)) {
            consume(TokenType.IDENTIFIER, "Expect supeclass name.");
            superclass = new Expr.Variable(previous());
        }
        consume(TokenType.LEFT_BRACE, "Expect '{' after class name.");
        List<Stmt.Function> methods = new ArrayList<>();
        while(!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            if (check(TokenType.INTERNAL)) {
                methods.add(function("private method"));
            } else {
                methods.add(function("method"));
            }
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' after class body.");
        return new Stmt.Class(name, superclass, methods);
    }
    private Stmt statement() {
        if(match(TokenType.PRINT)) return printStatement();
        if(match(TokenType.RETURN)) return returnStatement();
        if(match(TokenType.BREAK)) return breakStatement();
        if(match(TokenType.WHILE)) return whileStatemnt();
        if(match(TokenType.FOR)) return forStatement();
        if(match(TokenType.IF)) return ifStatement();
        if(match(TokenType.LEFT_BRACE)) return new Stmt.Block(block());
        return expressionStatement();
    }

    private Stmt forStatement() {
        consume(TokenType.LEFT_PAREN, "Expect '(' after  for.");
        Stmt initializer = null;
        if(match(TokenType.SEMICOLON)) {
            initializer = null;
        } else if (match(TokenType.LET)) {
            initializer = varDeclaration();
        } else {
            initializer = expressionStatement();
        }
        Expr condition = null;
        if(!check(TokenType.SEMICOLON)) {
            condition = expression();
        } 
        consume(TokenType.SEMICOLON, "Expect ';' after loop condition.");
        Expr increment = null;
        if(!check(TokenType.RIGHT_PAREN)) {
            increment = expression();
        }
        consume(TokenType.RIGHT_PAREN, "Expect ')' after for clauses.");
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
        consume(TokenType.LEFT_PAREN, "Expect '(' after if.");
        Expr condition = expression();
        consume(TokenType.RIGHT_PAREN, "Expect ')' after if condition");
        Stmt thenBrach = statement();
        Stmt elseBranch = null;
        if(match(TokenType.ELSE)) {
            elseBranch = statement();
        }
        return new Stmt.If(condition, thenBrach, elseBranch);
    }

    private Stmt printStatement() {
        Expr value =  expression();
        consume(TokenType.SEMICOLON, "Expect ';' after value", Mani.isStrictMode);
        return new Stmt.Print(value);
    }

    private Stmt returnStatement() {
        Token keyword = previous();
        Expr value = null;
        if(!check(TokenType.SEMICOLON)) {
            value = expression();
        }
        consume(TokenType.SEMICOLON, "Expect ';' after return.", Mani.isStrictMode);
        return new Stmt.Return(keyword, value);
    }
    private Stmt breakStatement() {
   	Token keyword = previous();
        consume(TokenType.SEMICOLON, "Expect ';' after break.", Mani.isStrictMode);
        return new Stmt.Break(keyword);
    } 
    private Stmt varDeclaration() {
        Token name = consume(TokenType.IDENTIFIER, "Expect a variable name.");
        Expr initializer = null;
        if(match(TokenType.EQUAL) || match(TokenType.VAR_ARROW)) {
            initializer = expression();
        }
        consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.", Mani.isStrictMode);
        return new Stmt.Var(name, initializer);
    }

    private Stmt whileStatemnt() {
        consume(TokenType.LEFT_PAREN, "Expect '(' after while.");
        Expr condition = expression();
        consume(TokenType.RIGHT_PAREN, "Expect ')' after while condition.");
        Stmt body = statement();
        return new Stmt.While(condition, body);
    }

    private Stmt expressionStatement() {
        Expr expr = expression();
        consume(TokenType.SEMICOLON, "Expect ';' after expression.", Mani.isStrictMode);
        return new Stmt.Expression(expr);
    }

    private Stmt.Function function(String kind) {
        Boolean isPrivate = false;
        if (previous().type == TokenType.INTERNAL || kind.equals("private method")) {
            isPrivate = true;
            advance();
        }
        Token name = consume(TokenType.IDENTIFIER, "Expect " + kind + " name.");
        consume(TokenType.LEFT_PAREN, "Expect '(' after " + kind + " name.");
        List<Token> parameters = new ArrayList<>();
        if(!check(TokenType.RIGHT_PAREN)) {
            do {
                if(parameters.size() >= 32) {
                    error(peek(), "Cannot have more than 32 parameters.");
                }
                parameters.add(consume(TokenType.IDENTIFIER, TokenType.MINUS_MINUS, "Expect parameter name or ArrayToken."));
            } while(match(TokenType.COMMA));
        }
        consume(TokenType.RIGHT_PAREN, "Expect ')' after parameters");
        consume(TokenType.LEFT_BRACE, "Expect '{' before " + kind + " body.");
        List<Stmt> body = block();
        return new Stmt.Function(name, parameters, body, isPrivate);
    } 

    private List<Stmt> block() {
        List<Stmt> statements = new ArrayList<>();
        while(!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            statements.add(declarations());
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' after block.");
        return statements;
    }
    //Expressions
    private Expr equality() {
        Expr expr = comparision();
        while(match(TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparision();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }
    
    private Expr comparision() {
        Expr expr  = addition();
        while(match(TokenType.GREATER, TokenType.LESS, TokenType.GREATER_EQUAL, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            Expr right = addition();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }
    private Expr addition() {
        Expr expr = multiplication();
        while(match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = multiplication();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr multiplication() {
        Expr expr = unary();
        while(match(TokenType.SLASH, TokenType.STAR, TokenType.PERCENT, TokenType.STAR_STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }
    private Expr unary() {
        if(match(TokenType.BANG, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }
        return call();
    }
    private Expr call() {
        Expr expr = primary();
        while(true) {
            if(match(TokenType.LEFT_PAREN)) {
                expr = finishCall(expr);
            } else if (match(TokenType.DOT)) {
                    Token name = consume(TokenType.IDENTIFIER, "Expect property name after '.' .");
                    expr = new Expr.Get(expr, name, (getAt(-3).type == TokenType.IDENTIFIER ? false : true));
            } else if(match(TokenType.PLUS_PLUS)) {
            	Token increment = new Token(TokenType.PLUS, "+", null, previous().line);
	        	if(expr instanceof Expr.Variable) {
	        		Token name = ((Expr.Variable)expr).name;
	                Expr.Variable lhs_var = new Expr.Variable(name);
	                Expr.Binary bin_expr = new Expr.Binary(lhs_var, increment, new Expr.Literal(new Double(1)));
	                expr = new Expr.Assign(name, bin_expr);
	            } else if (expr instanceof Expr.Get) {
	                Expr.Get get = (Expr.Get) expr;
	                Expr.Binary bin_expr = new Expr.Binary(get, increment, new Expr.Literal(new Double(1)));
	                expr = new Expr.Set(get.object, get.name, bin_expr);
	            } else {
	            	error(previous(), "Invalid assignment target");
	            }
            } else if (match(TokenType.MINUS_MINUS)) {
            	Token decrement = new Token(TokenType.MINUS, "-", null, previous().line);
            	if(expr instanceof Expr.Variable) {
            		Token name = ((Expr.Variable)expr).name;	                 
            		Expr.Variable lhs_var = new Expr.Variable(name);
	                Expr.Binary bin_expr = new Expr.Binary(lhs_var, decrement, new Expr.Literal(new Double(1)));
	                expr = new Expr.Assign(name, bin_expr);
	            } else if (expr instanceof Expr.Get) {
	                Expr.Get get = (Expr.Get) expr;
	                Expr.Binary bin_expr = new Expr.Binary(get, decrement, new Expr.Literal(new Double(1)));
                    expr = new Expr.Set(get.object, get.name, bin_expr);
	            } else {
	            	error(previous(), "Invalid assignment target");
	            }
            } else {
                break;
            }
        }
        return expr;
    }

    private Expr finishCall(Expr callee) {
        List<Expr> arguments = new ArrayList<>();
        if(!check(TokenType.RIGHT_PAREN)) {
            do {
                if(arguments.size() >= 32) {
                    error(peek(), "Cannot have more than 32 arguments.");
                }
                arguments.add(expression());
            } while(match(TokenType.COMMA));
        }
        Token paren = consume(TokenType.RIGHT_PAREN, "Expect ')' after arguments.");
        return new Expr.Call(callee, paren, arguments);
    }

    private Expr primary() {
        if(match(TokenType.FALSE)) return new Expr.Literal(false);
        if(match(TokenType.TRUE)) return new Expr.Literal(true);
        if(match(TokenType.NIL)) return new Expr.Literal(null);

        if(match(TokenType.SUPER)) {
            Token keyword = previous();
            consume(TokenType.DOT, "Expect '.' after super.");
            Token method = consume(TokenType.IDENTIFIER, "Expect superclass method name.");
            return new Expr.Super(keyword, method);
        }
        if(match(TokenType.THIS)) return new Expr.This(previous());
        if(match(TokenType.IDENTIFIER)) return new Expr.Variable(previous());
        //** Adding the ++a and the --a feature (does it by 2) */
        if (match(TokenType.PLUS_PLUS)){
            Token increment = new Token(TokenType.PLUS, "+", null, previous().line);
            Expr found = null;
            if (peek().type == TokenType.IDENTIFIER) {
                found = new Expr.Variable(peek());
                advance();
                Token name = ((Expr.Variable) found).name;
                Expr.Variable lhs_var = new Expr.Variable(name);
                Expr.Binary bin_expr = new Expr.Binary(lhs_var, increment, new Expr.Literal(new Double(2)));
                return new Expr.Assign(name, bin_expr);
            } else {
                error(previous(), "Invalid assignment target");
            }
        } 
        if (match(TokenType.MINUS_MINUS)) {
            Token decrement = new Token(TokenType.MINUS, "-", null, previous().line);
            Expr found = null;
            if (peek().type == TokenType.IDENTIFIER) {
                found = new Expr.Variable(peek());
                advance();
                Token name = ((Expr.Variable) found).name;
                Expr.Variable lhs_var = new Expr.Variable(name);
                Expr.Binary bin_expr = new Expr.Binary(lhs_var, decrement, new Expr.Literal(new Double(2)));
                return new Expr.Assign(name, bin_expr);
            } else {
                error(previous(), "Invalid assignment target");
            }
        }
        if(match(TokenType.NUMBER, TokenType.STRING)) {
            return new Expr.Literal(previous().literal);
        }
        if(match(TokenType.LEFT_PAREN)) {
            Expr expr = expression();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }
        throw error(peek(), "Expect expression.");
    }
    //UTILS
    private Token consume(TokenType type, String message) {
        if(check(type)) return advance();
        throw error(peek(), message);
    }

    private Token consume(TokenType type1, TokenType type2, String message) {
        if (check(type1) || check(type2)) return advance();
        throw error(peek(), message);
    }

    private Token consume(TokenType type, String message, Boolean useStrict) {
        if (useStrict) {
            return consume(type, message);
        } else {
            return advance();
        }
    } 

    private ParserError error(Token token, String message) {
        Mani.error(token, message);
        return new ParserError();
    }

    private void synchnorize() {
        advance();
        while(!isAtEnd()) {
            if(previous().type == TokenType.SEMICOLON) return;
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
        return peek().type == TokenType.EOF;
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
    private Token getAt(int pos) {
        return tokens.get(current + pos);
    }
}
