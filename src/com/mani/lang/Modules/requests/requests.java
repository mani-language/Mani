/*
 * Copyright 2019 This source file is part of the Máni open source project
 *
 * Copyright (c) 2018 - 2019.
 *
 * Licensed under Mozilla Public License 2.0
 *
 * See https://github.com/mani-language/Mani/blob/master/LICENSE.md for license information.
 */

package com.mani.lang.Modules.requests;

import com.mani.lang.Modules.Module;
import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.domain.ManiCallableInternal;
import com.mani.lang.exceptions.GeneralError;
import com.mani.lang.local.Locals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class requests implements Module {
    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("request", new ManiCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) throws GeneralError {
                if (arguments.get(0) instanceof String) {
                    return new Request((String) arguments.get(0));
                } else {
                    throw new GeneralError("Input must be a String");
                }
            }
        });
    }

    @Override
    public boolean hasExtensions() {
        return true;
    }

    @Override
    public Object extensions() {
        HashMap<String, HashMap<String, ManiCallableInternal>> db = new HashMap<>();
        HashMap<String, ManiCallableInternal> locals = new HashMap<>();

        locals.put("doGet", new ManiCallableInternal() {
           @Override
           public Object call(Interpreter interpreter, List<Object> arguments) {
               try {
                   ((Request) this.workWith).doGet();
               } catch (IOException e) {
                   throw new GeneralError("Cannot set request type to GET, was the request created properly?");
               }
               return this.workWith;
           }
        });

        locals.put("getResponseCode", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return ((Request) this.workWith).getResponse();
            }
        });

        locals.put("setHeaders", new ManiCallableInternal() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(Locals.getType(arguments.get(0)).equalsIgnoreCase("map"))) {
                    throw new GeneralError("Map must be provided as argument.");
                }
                ((Request) this.workWith).setHeaders((Map<Object, Object>)arguments.get(0));
                return this.workWith;
            }
        });

        locals.put("responseToText", new ManiCallableInternal() {
            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                try {
                    return ((Request) this.workWith).responseToText();
                } catch (IOException e) {
                    throw new GeneralError("Couldn't get response message. Was the request created correctly?");
                }
            }
        });

        db.put("request", locals);
        return db;
    }

    public class Request {

        public Request(String location) {
            this.location = location;
            try {
                this.url = new URL(this.location);
            } catch (MalformedURLException e) {
                throw new GeneralError("URL cannot be created. Is it a real url?");
            }
            try {
                this.httpURLConnection = (HttpURLConnection) this.url.openConnection();
            } catch (IOException e) {
                throw new GeneralError("URL cannot be opened. Is it a real url? Do you have internet?");
            }

        }

        public Object setHeaders(Map<Object, Object> values) {
            for (Object obj : values.keySet()) {
                this.httpURLConnection.setRequestProperty(String.valueOf(obj), String.valueOf(values.get(obj)));
            }
            return this;
        }

        public Object doGet() throws IOException {
            this.httpURLConnection.setRequestMethod("GET");
            return this;
        }

        public Object getResponse() throws GeneralError {
            try {
                return this.httpURLConnection.getResponseCode();
            } catch (Exception e) {
                throw new GeneralError("Simply cannot get response code from url.");
            }
        }

        public Object responseToText() throws IOException {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }

        private String location;
        private URL url;
        private HttpURLConnection httpURLConnection;
    }
}
