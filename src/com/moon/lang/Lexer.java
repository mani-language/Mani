package com.moon.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.moon.lang.TokenType.*;

class Lexer {
     private static final Map<String, TokenType> keywords;
    static{
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("for", FOR);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("fn", FN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("say", PRINT);
        keywords.put("fag", FAG);
        keywords.put("or", OR);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this",THIS);
        keywords.put("true",TRUE);
        keywords.put("while",WHILE);
        keywords.put("let", LET);
        keywords.put("break", BREAK);
        keywords.put("loop", LOOP);
    }
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    Lexer(String source){
        this.source = source;
    }
    List<Token> scanTokens(){
        while(! isAtEnd()){
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;

    }
    private void scanToken(){
        char c = advance();
        switch(c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case '[': addToken(LEFT_SQUARE); break;
            case ']': addToken(RIGHT_SQUARE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case ';': addToken(SEMICOLON); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case '*': addToken(STAR); break;
            case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '>': addToken(match('=')? GREATER_EQUAL : GREATER); break;
            case '<': addToken(match('=')? LESS_EQUAL : match('-') ? VAR_ARROW : LESS); break;
            case '/':
                if(match('/')){
                    while(peek() != '\n' && !isAtEnd()) advance();
                 }  else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                //ignore whitespaces
                break;
            case '\n':
                line++;
                break;
            case '"': string(); break;
            default:
                if(isDigit(c)){
                    number();
                } else if(isAlpha(c)){
                    identifier();
                }
                else {
                    Moon.error(line, "Unexpected character");
                }
                break;
        }
    }
    private void identifier(){
        while(isAlphaNumeric(peek())) advance();
        String text = source.substring(start,current);
        TokenType type  = keywords.get(text);
        if(type == null) type = IDENTIFIER;
        addToken(type);
    }
    private void number(){
        while(isDigit(peek())) advance();
        //look for the fractional part and consume '.'
        if(peek() == '.' && isDigit(peekNext())){
            advance();
        }
        while(isDigit(peek())) advance();
        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }
    private void string(){
        while(peek() != '"' && !isAtEnd()){
            if (peek() == '\n') line++;
            advance();
        }
        if(isAtEnd()){
            Moon.error(line, "Unterminated string");
            return;
        }
        //The closing "
        advance();
        //Trim the surrounding quotes
        String value = source.substring(start+1, current-1);
        addToken(STRING, value);
    }

    private boolean match(char expected){
        if(isAtEnd()) return false;
        if(source.charAt(current) != expected) return false;
        current++;
        return true;
    }
    private char peek(){
        if(isAtEnd()) return '\0';
        return source.charAt(current);
    }
    private char peekNext(){
        if(current + 1 > source.length()) return '\0';
        return source.charAt(current + 1);
    }
    private boolean isAlpha(char c){
        return(c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }
    private boolean isAlphaNumeric(char c){
        return isAlpha(c) || isDigit(c);
    }
    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }
    private boolean isAtEnd(){
        return current >= source.length();
    }
    private char advance(){
        current++;
        return source.charAt(current-1);
    }
    private void addToken(TokenType type){
        addToken(type, null);
    }
    //we use overloading for tokens with literals
    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}