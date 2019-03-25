package com.mani.lang.language;

import com.mani.lang.TokenType;

import java.util.Map;

public class German implements Lang {

    @Override
    public Object init(Map<String, String> keywords) {
        keywords.put("say", "sagen");
        return keywords;
    }
}
