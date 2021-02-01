package com.xuj.lib.db.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Container {
	public static Map<String, String> sortMapByKey(Map<String, String> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, String> sortedMap = new TreeMap<String, String>(
				new Comparator<String>() {
					public int compare(String key1, String key2) {
						return key1.compareTo(key2);
					}
				});
		sortedMap.putAll(oriMap);
		return sortedMap;
	}
}
