package com.xuj.lib.db.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Preferences {
	private static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static int getValue(Context context, String key, int defValue) {
		return getSharedPreferences(context).getInt(key, defValue);
	}

	public static long getValue(Context context, String key, long defValue) {
		return getSharedPreferences(context).getLong(key, defValue);
	}

	public static float getValue(Context context, String key, float defValue) {
		return getSharedPreferences(context).getFloat(key, defValue);
	}

	public static boolean getValue(Context context, String key, boolean defValue) {
		return getSharedPreferences(context).getBoolean(key, defValue);
	}

	public static String getValue(Context context, String key, String defValue) {
		return getSharedPreferences(context).getString(key, defValue);
	}

	public static Map<String, ?> getAll(Context context) {
		return getSharedPreferences(context).getAll();
	}

	public static Map<String, String> getAll(Context context, String headInfo) {
		Map<String, String> re = new HashMap<String, String>();
		Map<String, ?> all = Preferences.getAll(context);
		Set<String> keys = all.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			if (key.startsWith(headInfo)) {
				re.put(subStringTail(key, headInfo), "" + all.get(key));
			}
		}
		return (re.size() > 1) ? Container.sortMapByKey(re) : re;
	}

	private static Editor getEditor(Context context) {
		return getSharedPreferences(context).edit();
	}

	public static boolean setValue(Context context, String key, int value) {
		Editor mEditor = getEditor(context);
		mEditor.putInt(key, value);
		return mEditor.commit();
	}

	public static boolean setValue(Context context, String key, long value) {
		Editor mEditor = getEditor(context);
		mEditor.putLong(key, value);
		return mEditor.commit();
	}

	public static boolean setValue(Context context, String key, float value) {
		Editor mEditor = getEditor(context);
		mEditor.putFloat(key, value);
		return mEditor.commit();
	}

	public static boolean setValue(Context context, String key, boolean value) {
		Editor mEditor = getEditor(context);
		mEditor.putBoolean(key, value);
		return mEditor.commit();
	}

	public static boolean setValue(Context context, String key, String value) {
		Editor mEditor = getEditor(context);
		mEditor.putString(key, value);
		return mEditor.commit();
	}

	public static boolean setValue(Context context, String keyHead,
                                   Map<String, String> values) {
		boolean re = true;
		Set<String> key = values.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			Editor mEditor = getEditor(context);
			mEditor.putString(keyHead + s, values.get(s));
			re = mEditor.commit();
		}
		return re;

	}

	public static boolean remove(Context context, String key) {
		boolean re = true;
		Editor mEditor = getEditor(context);
		mEditor.remove(key);
		re = mEditor.commit();
		return re;

	}

	private static String subStringTail(String data, String headInfo) {
		String re = "";
		int index = data.indexOf(headInfo);
		re = data.substring(index + headInfo.length());
		return re;
	}
}
