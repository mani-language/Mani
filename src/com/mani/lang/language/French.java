/*
 * Copyright 2019 Máni.
 */
package com.mani.lang.language;

import java.util.Map;

public class French implements Lang {

    @Override
    public Object init(Map<String, String> keywords) {
        keywords.put("say", "afficher");
        keywords.put("break", "interrompre");
        keywords.put("return", "retour");
        keywords.put("class", "classe");
        keywords.put("and", "et");
        keywords.put("or", "ou");
        keywords.put("false", "faux");
        keywords.put("true", "vrais");
        keywords.put("this", "ce");
        keywords.put("loop", "boucle");
        keywords.put("while", "tandis");
        keywords.put("STRICT", "STRICT");
        keywords.put("internal", "interne");
        keywords.put("for", "pour");
        keywords.put("else", "sinon");
        keywords.put("if", "si");
        keywords.put("nil", "néant");
        keywords.put("let", "laisser");
        keywords.put("load", "charger");


        return keywords;
    }
}