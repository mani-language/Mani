package com.mani.lang.Modules.datetime;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Modules.Module;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public final class datetime implements Module {


    private void initConstants(Interpreter interpreter) {
        interpreter.define("STYLE_FULL", new Double(DateFormat.FULL));
        interpreter.define("STYLE_LONG", new Double(DateFormat.LONG));
        interpreter.define("STYLE_MEDIUM", new Double(DateFormat.MEDIUM));
        interpreter.define("STYLE_SHORT", new Double(DateFormat.SHORT));
    }

    @Override
    public void init(Interpreter interpreter) {
        initConstants(interpreter);
        interpreter.addSTD("DTnewDate", new datetime_newDate());
        //interpreter.addSTD("DTnewFormat", new datetime_newFormat());
        //interpreter.addSTD("DTFormatDate", new datetime_format());
        //interpreter.addSTD("DTparseDate", new datetime_parseDate());
        interpreter.addSTD("DTtoTimestamp", new ManiCallable(){
            
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                /**
                 * Arguments:
                 * 1 -  The date object.
                 */
                return new Double(((Calendar) arguments.get(0)).getTimeInMillis());
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