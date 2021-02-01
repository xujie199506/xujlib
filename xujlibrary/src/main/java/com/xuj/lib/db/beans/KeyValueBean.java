package com.xuj.lib.db.beans;

import java.io.Serializable;

public class KeyValueBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8901313201682807253L;
	String key;
	String value;

	public KeyValueBean() {
	}

	public KeyValueBean(String _key, String _value) {
		this.key = _key;
		this.value = _value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
