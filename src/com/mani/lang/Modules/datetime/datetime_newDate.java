package com.mani.lang.Modules.datetime;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public final class datetime_newDate implements ManiCallable {

    @Override
    public int arity() {
        return -1;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        switch(arguments.size()) {
            case 0:
                // date()
                calendar.setTimeInMillis(System.currentTimeMillis());
                break;
            case 1:
                // date(timestamp)
                // date("date")
                date(calendar, arguments.get(0));
                break;
            case 2:
                // date("pattern", "date")
                date(calendar, arguments.get(0), arguments.get(1));
                break;
            case 3:
            case 4:
                // date(year, month, day)
                calendar.set(
                    (new Double((Double) arguments.get(0)).intValue()),
                    (new Double((Double) arguments.get(1)).intValue()),
                    (new Double((Double) arguments.get(2)).intValue())
                );
            case 5:
                // date(year, month, day, hour, minute)
                calendar.set(
                    (new Double((Double) arguments.get(0)).intValue()),
                    (new Double((Double) arguments.get(1)).intValue()),
                    (new Double((Double) arguments.get(2)).intValue()),
                    (new Double((Double) arguments.get(3)).intValue()),
                    (new Double((Double) arguments.get(4)).intValue())
                    
                );
            case 6:
            default:
                // date(year, month, day, hour, minute, second)
                calendar.set(
                    (new Double((Double) arguments.get(0)).intValue()), 
                    (new Double((Double) arguments.get(1)).intValue()),
                    (new Double((Double) arguments.get(2)).intValue()),
                    (new Double((Double) arguments.get(3)).intValue()), 
                    (new Double((Double) arguments.get(4)).intValue()),
                    (new Double((Double) arguments.get(5)).intValue()) 
                    );
                break;
            
        }
        return calendar;
    }
    
    private static void date(Calendar calendar, Object arg1) {
        if (arg1 instanceof Double) {
            calendar.setTimeInMillis(new Double((Double) arg1).longValue());
            return;
        }
        try {
            calendar.setTime(DateFormat.getDateTimeInstance().parse(arg1.toString()));
        } catch (Exception ignore) {
        }
    }

    private static void date(Calendar calendar, Object arg1, Object arg2) {
        if (arg1 instanceof Double) {
            date(calendar, arg1);
            return;
        }
        try {
            calendar.setTime(new SimpleDateFormat(arg1.toString()).parse(arg2.toString()));
        } catch (Exception ignore) {
        }
    }

}