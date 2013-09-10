package usp.ime.line.ivprog.model.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import usp.ime.line.ivprog.controller.Services;

public class IVPObservableMap extends Observable {

	private HashMap map;
	private boolean isLocalVarMap;

	public IVPObservableMap(boolean isLocalVar) {
		setLocalVarMap(isLocalVar);
		map = new HashMap();
	}

	public void put(String key, Object o) {
		map.put(key, o);
		setChanged();
		notifyObservers(key);
	}

	public Object get(String key) {
		return map.get(key);
	}

	public Object remove(String key) {
		Object o = map.remove(key);
		setChanged();
		notifyObservers();
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