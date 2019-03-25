package com.mani.lang.language;

import com.mani.lang.TokenType;

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
        return keywords;
    }
}
