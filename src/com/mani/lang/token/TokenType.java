/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.token;
public enum TokenType {

    //Single-character Tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_SQUARE, RIGHT_SQUARE, COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH,
    STAR, PERCENT, IMPORT, COLON,

    //one or two character Tokens

    BANG, BANG_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, EQUAL, EQUAL_EQUAL, VAR_ARROW,

    // combined assignment ops
    PERCENT_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, SLASH_ASSIGN, STAR_ASSIGN, PLUS_PLUS, MINUS_MINUS, ASSIGN_ARROW, STAR_STAR,

    //Literals
    IDENTIFIER, NUMBER, STRING,

    //KEYWORDS

    AND, CLASS, ELSE, FALSE, FN, FOR, IF, NIL, OR, PRINT, RETURN, SUPER, THIS, TRUE, LET, WHILE, LOOP, BREAK, INTERNAL,
    DEPRECIATED, LOAD, STRICT, IS, AS, MATCH, CASE, CHANGELANG, NAMESET, SWITCH,

    //End
    EOF

}
