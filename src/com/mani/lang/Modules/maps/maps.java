package com.mani.lang.Modules.maps;

import com.mani.lang.Interpreter;
import com.mani.lang.Modules.Module;

public final class maps implements Module {

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("newMap", new maps_newMap());
        interpreter.addSTD("mapGetValue", new maps_mapGetValue()); 
        interpreter.addSTD("mapAddItem", new maps_mapAddItem());
        interpreter.addSTD("mapRemoveItem", new maps_mapRemoveItem());
        interpreter.addSTD("mapUpdateItem", new maps_mapUpdateItem());
        interpreter.addSTD("mapGetKeys", new maps_mapGetKeys());
        interpreter.addSTD("arraysToMap", new maps_arraysToMap());
	}

}