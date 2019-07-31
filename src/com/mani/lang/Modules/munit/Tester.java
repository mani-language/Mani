/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.munit;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    Tester() { }

    public String setHeader(String header) {
        this.header = header;
        return header;
    }

    public String setMessage(String message) {
        this.message = message;
        return message;
    }

    public boolean assertEquals(Object expected, Object actual) {
        this.testList.add(new Test(this.message, equals(expected, actual)));
        return equals(expected, actual);
    }

    public boolean assertEquals(Object expected, Object actual, String message) {
        this.message = message;
        return this.assertEquals(expected, actual);
    }

    public boolean assertTrue(Object expected) {
        boolean passed = expected.equals(true);
        this.testList.add(new Test(this.message, passed));
        return passed;
    }

    public boolean assertTrue(Object expected, String message) {
        this.message = message;
        return this.assertTrue(expected);
    }

    public boolean assertFalse(Object expected) {
        boolean passed = expected.equals(false);
        this.testList.add(new Test(this.message, passed));
        return passed;
    }

    public boolean assertFalse(Object expected, String message) {
        this.message = message;
        return this.assertFalse(expected);
    }

    private boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return (obj2 == null);
        }
        return obj1.equals(obj2);
    }

    public boolean getResults() {
        for (Test test : testList) {
            this.count++;
            if (test.isPassed()) {
                this.passed++;
            } else {
                this.failed++;
            }
        }
        return display();
    }

    private boolean display() {
        System.out.println((this.passed == this.count && this.failed == 0 ? " \u001B[102m\u001B[90m PASSED " : " \u001B[101m FAILED ") + "\u001B[0m\u001B[97m\u001B[1m " + this.header + "\u001B[49m\u001B[0m");
        for (Test test : testList) {
            System.out.println(test);
        }
        if (this.passed != this.count && this.failed >= 0) {
            System.exit(1);
        }
        return (this.passed == this.count && this.failed == 0);
    }

    private int count = 0;
    private int passed = 0;
    private int failed = 0;
    private String message = "";
    private String header = "";
    private List<Test> testList = new ArrayList<>();

    private static class Test {
        Test(String message, boolean passed) {
            this.message = message;
            this.passed = passed;
        }

        public boolean isPassed() { return passed; }
        public String getMessage() { return message; }

        public void setPassed(boolean passed) { this.passed = passed; }
        public void setMessage(String message) { this.message = message; }

        @Override
        public String toString() {
            return "\t" + (this.passed ? "\u001B[92m✔" : "\u001B[91m✘") + "\u001B[90m \"" + this.message + "\"\u001B[0m";
        }

        private boolean passed;
        private String message;
    }
}
