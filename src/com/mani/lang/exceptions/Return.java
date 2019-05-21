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