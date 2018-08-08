package usp.ime.line.ivprog.model.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

public class IVPVariableMap {
	
	private HashMap map;
	private boolean isLocalVarMap;

	public IVPVariableMap(boolean isLocalVar) {
		setLocalVarMap(isLocalVar);
		map = new HashMap();
	}

	public void put(String key, Object o) {
		map.put(key, o);
	}

	public Object get(String key) {
		return map.get(key);
	}

	public Object remove(String key) {
		Object o = map.remove(key);
		return o;
	}

	public Vector toVector() {
		Iterator i = map.keySet().iterator();
		Vector v = new Vector();
		while (i.hasNext()) {
			v.add(map.get(i.next()));
		}
		return v;
	}

	public boolean isLocalVarMap() {
		return isLocalVarMap;
	}

	public void setLocalVarMap(boolean isLocalVarMap) {
		this.isLocalVarMap = isLocalVarMap;
	}
}