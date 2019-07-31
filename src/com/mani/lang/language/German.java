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

public class German implements Lang {

    @Override
    public Object init(Map<String, String> keywords) {
        keywords.put("say", "sagen");
        keywords.put("break", "brechen");
        keywords.put("return", "rückkehr");
        keywords.put("class", "klasse");
        keywords.put("and", "und");
        keywords.put("or", "oder");
        keywords.put("false", "falsch");
        keywords.put("true", "wahr");
        keywords.put("this", "diese");
        keywords.put("loop", "schleife");
        keywords.put("while", "während");
        keywords.put("STRICT", "STRENG");
        keywords.put("internal", "privatgelände");
        keywords.put("for", "zum");
        keywords.put("else", "sonst");
        keywords.put("if", "ob");
        keywords.put("nil", "null");
        keywords.put("let", "lassen");
        keywords.put("load", "belastung");


        return keywords;
    }
}
