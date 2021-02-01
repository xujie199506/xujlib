package com.xuj.lib.db;


import com.xuj.lib.db.beans.KeyValueBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 查询结果集对象.<br>
 * <br>
 * CreateDate:   <br>
 * Copyright: Copyright(c)   <br>
 * Company:   <br>
 *
 * @since v1.0.0
 * @Description  ::创建此类</br>
 */
public class Resultobj {
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String[]> datas = new ArrayList<String[]>();

	/**
	 * 加入一行数据.<br>
	 * <br>
	 *
	 * @param cols
	 * @Description  ::创建此方法</br>
	 */
	public void add(String[] cols) {
		datas.add(cols);
	}

	/**
	 * 设置查询结果标题集合.<br>
	 * <br>
	 *
	 * @param titles
	 * @Description  ::创建此方法</br>
	 */
	public void setTitles(ArrayList<String> titles) {
		this.titles = titles;
	}

	/**
	 * 获取所有标题集合.<br>
	 * <br>
	 *
	 * @return
	 * @Description  ::创建此方法</br>
	 */
	public ArrayList<String> getTitles() {
		return titles;
	}

	/**
	 * 按指定行列获取数据.<br>
	 * <br>
	 *
	 * @return
	 * @Description  ::创建此方法</br>
	 */
	public int columCount() {
		return titles.size();
	}

	/**
	 * 获取总行数.<br>
	 * <br>
	 *
	 * @return
	 * @Description  ::创建此方法</br>
	 */
	public int rowCount() {
		return datas.size();
	}

	/**
	 * 获取标题名称.<br>
	 * <br>
	 *
	 * @param col
	 * @return
	 * @Description 2013-6-19::   ::创建此方法</br>
	 */
	public String getTitleName(int col) {
		String re = "";

		String[] rows = datas.get(0);
		if (rows != null) {
			re = rows[col];
		}

		return re;
	}

	/**
	 * 获取指定行列的值.<br>
	 * <br>
	 *
	 * @param row
	 *            行（从0开始）
	 * @param col
	 *            列（从0开始）
	 * @return
	 * @Description  ::创建此方法</br>
	 */
	public String getValue(int row, int col) {
		String re = "";

		String[] rows = datas.get(row);
		if (rows != null) {
			re = rows[col];
		}

		return re;
	}

	/**
	 * 按指定行数和标题获取数据.<br>
	 * <br>
	 *
	 * @param row
	 *            行（从0开始）
	 * @param title
	 *            列（从0开始）
	 * @return
	 * @Description  ::创建此方法</br>
	 */
	public String getValue(int row, String title) {
		int col = 0;
		for (int i = 0; i < titles.size(); i++) {
			if (titles.get(i).equalsIgnoreCase(title)) {
				col = i;
				break;
			}
		}

		return getValue(row, col);
	}

	public List<KeyValueBean> toKeyValueBeans(String key, String value) {
		List<KeyValueBean> kvbList = new ArrayList<KeyValueBean>();
		try {
			for (int i = 0; i < datas.size(); i++) {
				KeyValueBean kvb = new KeyValueBean(getValue(i, key), getValue(
						i, value));
				kvbList.add(kvb);
			}
			return kvbList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
