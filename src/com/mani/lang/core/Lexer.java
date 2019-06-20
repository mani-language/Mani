package com.mani.lang.core;

import com.mani.lang.main.Mani;
import com.mani.lang.token.Token;
import com.mani.lang.token.TokenType;
import com.mani.lang.language.Lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mani.lang.token.TokenType.*;

public class Lexer {
    private static Map<String, TokenType> keywords;

    private boolean awaitingLangName = false;
    private String customLangName = "";
    private String lastLangName = "english";
    private char lastStringChar = 'E'; // Using 'E' as a placeholder.

    static{
        keywords = new HashMap<>();
        keywords.put("STRICT", STRICT);
        keywords.put("CHANGE_LANG", CHANGELANG);
        keywords.put("and", AND);
        keywords.put("internal", INTERNAL);
        keywords.put("class", CLASS);
        keywords.put("for", FOR);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("fn", FN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("say", PRINT);
        keywords.put("or", OR);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this",THIS);
        keywords.put("true",TRUE);
        keywords.put("while",WHILE);
        keywords.put("let", LET);
        keywords.put("break", BREAK);
        keywords.put("loop", LOOP);
        keywords.put("load", LOAD);
        keywords.put("is", IS);
        keywords.put("as", AS);
        keywords.put("match", MATCH);
        keywords.put("case", CASE);
        keywords.put("nameset", NAMESET);
    }
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    public Lexer(String source){
        this.source = source;
    }
    public List<Token> scanTokens(){
        while(! isAtEnd()){
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;

    }
    private void scanToken(){
        // Just going to check for a new language to load.
        if (customLangName != "" && !(customLangName.equalsIgnoreCase(lastLangName))) {
            loadLanguage();
        }

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
            case '-': addToken(match('=') ? MINUS_ASSIGN : match('-') ? MINUS_MINUS : match('>') ? ASSIGN_ARROW : MINUS); break;
            case '+': addToken(match('=') ? PLUS_ASSIGN : match('+') ? PLUS_PLUS : PLUS); break;
            case '*': addToken(match('=') ? STAR_ASSIGN : match('*') ? STAR_STAR : STAR); break;
            case '%': addToken(match('=') ? PERCENT_ASSIGN : PERCENT); break;
            case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            case '<': addToken(match('=') ? LESS_EQUAL : match('-') ? VAR_ARROW : match('<') ? VAR_ARROW : LESS); break;
            case ':': addToken(COLON); break;
            case '#': addToken(IMPORT); break;
            case '/':
                if(match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else if (match('*')) {
                    do {
                        advance();
                    } while(!(match('*') && match('/')) && !isAtEnd());
                } else {
                    addToken(match('=') ? SLASH_ASSIGN : SLASH);
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
            case '\'':
                this.lastStringChar = (this.lastStringChar == 'E' ? '\'' : this.lastStringChar);
                string();
                break;
            case '"':
                this.lastStringChar = (this.lastStringChar == 'E' ? '"' : this.lastStringChar);
                string();
                break;
            default:
                if(isDigit(c)){
                    number();
                } else if(isAlpha(c)){
                    identifier();
                }
                else {
                    Mani.error(line, "Unexpected character");
                }
                break;
        }
    }
    private void loadLanguage() {
        try {
            Map<String, TokenType> newLang = new HashMap<>();
            Map<String, String> trans = new HashMap<>();
            final Lang module = (Lang) Class.forName("com.mani.lang.language." + customLangName).newInstance();
            trans = (Map<String, String>) module.init(trans);
            for (String key : keywords.keySet()) {
                if (trans.containsKey(key)) {
                    newLang.put(trans.get(key), keywords.get(key));
                } else {
                    newLang.put(key, keywords.get(key));
                }
            }
            keywords = newLang;
            // Resetting so we don't keep loading th the language.
            lastLangName = customLangName;
            customLangName = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void identifier(){
        while(isAlphaNumeric(peek())) advance();
        String text = source.substring(start,current);
        TokenType type = keywords.get(text);
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
        while((peek() != this.lastStringChar) && !isAtEnd()){
            if (peek() == '\n') line++;
            advance();
        }
        this.lastStringChar = 'E'; // Resetting to the placeholder.
        if(isAtEnd()){
            Mani.error(line, "Unterminated string");
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

    private boolean match(char expected, int place) {
        if (isAtEnd() || isAtEnd(place)) return false;
        if(source.charAt(current + place) != expected) return false;
        current += 1 + place;
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
    private boolean isAtEnd(int location) {
        return (current + location) >= source.length();
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
        // We are going to check through each added token, for the CHANGELANG
        // token. If that is found, then the next token will be the language
        // to change it to.
        if (type == TokenType.CHANGELANG) {
            awaitingLangName = true;
            return;
        } else if (type == TokenType.STRING && awaitingLangName) {
            customLangName = (String) literal;
            awaitingLangName = false;
            advance(); // Remove the ";"
            return;
        }
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}