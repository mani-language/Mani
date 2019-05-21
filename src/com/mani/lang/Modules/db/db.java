package com.mani.lang.Modules.db;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.Modules.Module;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;

public class db implements Module {

    MongoClient cli;
    DB database = null;
    DBCollection collection = null;

    {
        try {
            cli = new MongoClient();
        } catch (UnknownHostException e) {
            // We will say nothing, as we cannot assume the
            // user will use the default localhost for their mongo instance.
        }
    }

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("db_connect", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                try {
                    cli = new MongoClient(new MongoClientURI((String) arguments.get(0)));
                    return "Connected!";
                } catch (UnknownHostException e) {
                    return e.toString();
                }
            }
        });
        interpreter.addSTD("db_database", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                cli.getDB((String) arguments.get(0));
                return "Connected to " + arguments.get(0).toString();
            }
        });
        interpreter.addSTD("db_collection", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                collection = database.getCollection((String) arguments.get(0));
                return "Collection is: " + arguments.get(0).toString();
            }
        });
        interpreter.addSTD("db_insert", new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (arguments.get(0) instanceof DBObject) {
                    collection.insert((DBObject) arguments.get(0));
                    return "Added";
                }
                return "Sorry, you can only commit DB objects.";
            }
        });
    }

    @Override
    public boolean hasExtensions() {
        return false;
    }

    @Override
    public Object extensions() {
        return null;
    }

}
