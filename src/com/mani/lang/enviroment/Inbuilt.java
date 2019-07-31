/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.enviroment;

import com.mani.lang.main.Mani;
import com.mani.lang.main.Std;
import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Inbuilt {

    /**
     * This file creates a list called "inBuilts" that contains functions / API's that can be used by the stdlib
     * This is loaded before anything else.
     */


    public static Map<String, ManiCallable> inBuilts = new HashMap<>();
}
