/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.language;

import java.util.Map;


public class Spanish implements Lang {

    @Override
    public Object init(Map<String, String> keywords) {
        keywords.put("say", "contar");
        keywords.put("break", "descanso");
        keywords.put("return", "regreso");
        keywords.put("class", "clase");
        keywords.put("and", "y");
        keywords.put("or", "o");
        keywords.put("false", "falso");
        keywords.put("true", "cierto");
        keywords.put("this", "esto");
        keywords.put("loop", "lazo");
        keywords.put("while", "mientras");
        keywords.put("STRICT", "ESTRICTO");
        keywords.put("internal", "privado");
        keywords.put("for", "para");
        keywords.put("if", "si");
        keywords.put("nil", "null");
        keywords.put("let", "dejar");
        keywords.put("load", "carga");


        return keywords;
    }
}
