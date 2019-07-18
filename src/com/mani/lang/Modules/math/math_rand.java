package com.mani.lang.Modules.math;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.main.Std;

import java.util.List;
import java.util.Random;

public class math_rand implements ManiCallable {
    @Override
    public int arity() {
        return -1;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        if (arguments.size() > 2) {
            return "Too many arguments";
        }

        Random rand = new Random();

        if (arguments.size() == 1 && arguments.get(0) instanceof Double) {
            return Std.makeDouble(rand.nextInt( Std.DoubleToInt((Double) arguments.get(0)) ));
        } else if (arguments.size() == 1 && arguments.get(0) instanceof String) {
            Long to = Long.valueOf((String) ((String) arguments.get(0)).substring(1, ((String) arguments.get(0)).length() - 1));
            Long from = 0L;
            final long randomLong = rand.nextLong() >>> 1;
            return (randomLong % (to - from) + from);
        } else if (arguments.size() == 2) {
            int max = new Double((Double) arguments.get(1)).intValue();
            int min = new Double((Double) arguments.get(0)).intValue();
            return Std.makeDouble(rand.nextInt(max - min + 1) + min);
        }

        return rand.nextDouble();
    }
}
