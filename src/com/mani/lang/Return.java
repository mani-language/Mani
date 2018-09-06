package com.mani.lang;

class Return extends RuntimeException {
    final Object value;

    Return(Object value) {
        this.value = value;
    }

    Return() {
        value = null;
    }
}