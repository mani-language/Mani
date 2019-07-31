/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.exceptions;

import com.mani.lang.token.Token;

public class RuntimeError extends RuntimeException {
    public final Token token;
    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}
