/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.domain;

import com.mani.lang.exceptions.RuntimeError;
import com.mani.lang.token.Token;
import com.mani.lang.core.Interpreter;

import java.util.HashMap;
import java.util.Map;

public class ManiInstance {

    private ManiClass klass;
    final private Map<String, Object> fields = new HashMap<>();

    ManiInstance(ManiClass klass) {
        this.klass = klass;
    } 

    public Object get(Token name) {
        if(fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }
        
        ManiFunction method = klass.findMethod(this, name.lexeme);
        if(method != null) return method;
        throw new RuntimeError(name, "Undefined Property '" + name.lexeme +"'.");
    }

    public String runShowFn(Interpreter inter) {
        return (String) this.klass.findMethod(this, "show").call(inter, null);
    }

    public boolean hasShowFn() {
        return this.klass.findMethod(this, "show") != null;
    }

    public boolean hasFunction(String functionName) {
        return this.klass.findMethod(this, functionName) != null;
    }

    public void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    public String getClassName() {
        return klass.getName();
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}