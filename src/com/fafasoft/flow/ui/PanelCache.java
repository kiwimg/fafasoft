package com.fafasoft.flow.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PanelCache {
	private static PanelCache panelCache = null;
	private HashMap cmap = null;
	static {
		panelCache = new PanelCache();
	}

	public static PanelCache getInstance() {
		if (panelCache == null) {
			panelCache = new PanelCache();
		}
		return panelCache;
	}

	private PanelCache() {
		cmap = new HashMap();
	}

	public void put(String key, Object value) {
		cmap.put(key, value);
	}

	public Object get(String key) {
		return cmap.get(key);
	}
	
	public void remove(String key) {
		Object ob = cmap.remove(key);
		ob = null;
	}
	public void removeAll() {
		cmap.clear();
	
	}
	public void removeOther(String key) {
		Object object = get(key);
		removeAll();
		put(key, object); 
	}
}
