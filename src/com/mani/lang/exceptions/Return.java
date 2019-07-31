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

public class Return extends RuntimeException {
    public final Object value;

    public Return(Object value) {
        this.value = value;
    }

    public Return() {
        value = null;
    }
}