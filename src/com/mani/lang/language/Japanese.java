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

public class Japanese implements Lang {

    @Override
    public Object init(Map<String, String> keywords) {
        keywords.put("say", "いう");
        keywords.put("break", "ブレーク");
        keywords.put("return", "戻る");
        keywords.put("class", "クラス");
        keywords.put("and", "そして");
        keywords.put("or", "または");
        keywords.put("false", "偽");
        keywords.put("true", "本当の");
        keywords.put("this", "この");
        keywords.put("loop", "ループ");
        keywords.put("while", "しながら");
        keywords.put("STRICT", "厳格");
        keywords.put("internal", "内部");
        keywords.put("for", "にとって");
        keywords.put("else", "さもないと");
        keywords.put("if", "もし");
        keywords.put("nil", "ヌル");
        keywords.put("let", "セット");
        keywords.put("load", "負荷");


        return keywords;
    }
}
