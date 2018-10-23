package com.mani.lang.Modules.std;

import java.util.List;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;

public final class std_charAt implements ManiCallable {

    @Override
    public int arity() {
        return 2;
    }

    @Override
	public Object call(Interpreter interpreter, List<Object> arguments) {
        if (arguments.size() == 2) {
            if (arguments.get(0) instanceof Double && arguments.get(1) instanceof String) {
                final String str = (String) arguments.get(1);
                final int in = new Double((Double) arguments.get(0)).intValue();
                
                return str.charAt(in);

            } 
        }
		return "Requires arguements: position, string";
	}

}