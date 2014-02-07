package usp.ime.line.ivprog.model.utils;

import java.util.HashMap;

public class IVPMapping {

    private HashMap mapping;

    public IVPMapping() {
        mapping = new HashMap();
    }

    public Object getObject(String key) {
        return mapping.get(key);
    }

    public void addToMap(String key, Object o) {
        mapping.put(key, o);
    }

}
