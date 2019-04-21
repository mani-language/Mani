package com.mani.lang.Modules.math;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Modules.Module;
import com.mani.lang.Modules.std.std_toString;

import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

public class math implements Module {

    private static final DoubleFunction<Double> doubleToNumber = Double::valueOf;

    @Override
    public void init(Interpreter interpreter) {
        // Defining common Math variables.
        interpreter.define("PI", Math.PI);
        interpreter.define("E", Math.E);

        // Defining the functions.
        interpreter.addSTD("floor", new math_floor());
        interpreter.addSTD("ceil", new math_ceil());
        interpreter.addSTD("rand", new math_rand());
        interpreter.addSTD("acos", functionConvert(Math::acos));
        interpreter.addSTD("asin", functionConvert(Math::asin));
        interpreter.addSTD("atan", functionConvert(Math::atan));
        interpreter.addSTD("atan2", biFunctionConvert(Math::atan2));
        interpreter.addSTD("cbrt", functionConvert(Math::cbrt));
        interpreter.addSTD("ceil", functionConvert(Math::ceil));
        interpreter.addSTD("cos", functionConvert(Math::cos));
        interpreter.addSTD("cosh", functionConvert(Math::cosh));
        interpreter.addSTD("exp", functionConvert(Math::exp));
        interpreter.addSTD("expm1", functionConvert(Math::expm1));
        interpreter.addSTD("floor", functionConvert(Math::floor));
    }

    private static ManiCallable functionConvert(DoubleUnaryOperator op) {
        return new ManiCallable() {
            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return doubleToNumber.apply(op.applyAsDouble((double) arguments.get(0)));
            }
        };
    }

    private static ManiCallable biFunctionConvert(DoubleBinaryOperator op) {
        return new ManiCallable() {
            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return doubleToNumber.apply(op.applyAsDouble((double) arguments.get(0), (double) arguments.get(1)));
            }
        };
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
