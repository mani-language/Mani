package com.mani.lang;

import com.mani.lang.Token.Token;

class RuntimeError extends RuntimeException {
    final Token token;
    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}
