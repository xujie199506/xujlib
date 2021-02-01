package com.xuj.lib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataAnalyzer<T> {
	Sqlite db;
	private String TAG = DataAnalyzer.class.getName();

	public Sqlite getDataHelper() {
		return db;
	}

	public DataAnalyzer(Context context, String DBName) {
		db = Sqlite.getInstance(context, DBName);
	}

	public boolean updateOrInsert(String DBName, String TableName, T bean,
                                  String Where) {
		// 获得数据库表字段
		List<String> zdmc = extractStr(DBName, TableName);
		// 实体类反射
		ContentValues colValue = reflectBean(bean, (ArrayList<String>) zdmc);

		int result = -1;
		try {
			result = db.updateOrInsert(TableName, colValue, Where, null);
		} catch (Exception ex) {
			Log.d(TAG, "updateOrInsert Exception" + ex.getMessage());
		}
		return result != -1L;
	}

	public boolean insert(String DBName, String TableName, T bean) {
		List<String> zdmcList = extractStr(DBName, TableName);
		ContentValues colValues = reflectBean(bean, zdmcList);
		int result = -1;
		try {
			result = db.insert(TableName, colValues);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result != -1;
	}

	public Boolean update(String DBName, String TableName, T bean, String Where) {
		// 获得数据库表字段
		List<String> zdmc = extractStr(DBName, TableName);
		// 实体类反射
		ContentValues colValue = reflectBean(bean, (ArrayList<String>) zdmc);
		int result = -1;
		try {
			result = db.update(TableName, colValue, Where, null);
		} catch (Exception ex) {

		}
		return result != -1;
	}

	public List<T> query(String TableName, String Colums, String Where,
                         String Order, Class<?> c) {
		return query(TableName, Colums, Where, Order, null, c);
	}

	public List<T> query(String TableName, String Colums, String Where,
                         String Order, String limit, Class<?> c) {
		List<T> list = new ArrayList<T>();
		T obj = null;
		String[] colums = null;
		if (!Colums.equals("*")) {
			colums = Colums.split(",");
		}
		Cursor cur = db.query(TableName, colums, Where, null, Order, limit);
		Field[] fields = c.getDeclaredFields();// 得到所有属性
		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			// 创建泛型实体类
			try {
				obj = (T) c.newInstance();
			} catch (IllegalAccessException e1) {

				e1.printStackTrace();
			} catch (InstantiationException e1) {

				e1.printStackTrace();
			}
			list.add(obj);
			for (Field field : fields) {

				String fn = field.getName();// 得到实体类属性名
				int colIndex = cur.getColumnIndex(fn);// 通过属性名得到记录索引值
				if (colIndex == -1) {
					continue;
				}

				// 判断属性的类型

				try {
					field.setAccessible(true);
					if (field.getType() == String.class) {
						field.set(obj, cur.getString(colIndex));
					}
					if (field.getType() == Integer.class) {
						field.set(obj, cur.getInt(colIndex));
					}
					if (field.getType() == Long.class) {
						field.set(obj, cur.getLong(colIndex));
					}
					if (field.getType() == Float.class) {
						field.set(obj, cur.getFloat(colIndex));
					}
					if (field.getType() == Double.class) {
						field.set(obj, cur.getDouble(colIndex));
					}
					if (field.getType() == Date.class
							|| field.getType() == java.sql.Date.class) {
						if (cur.getString(colIndex) != null) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"dd-MM-yyyy");
							Date date;
							try {
								date = sdf.parse(cur.getString(colIndex));
								field.set(obj, date);
							} catch (ParseException e) {

								e.printStackTrace();
							}
						}

					}
					field.setAccessible(true);

				} catch (IllegalArgumentException e) {

					e.printStackTrace();
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				}

			}

		}
		cur.close();
		db.closeDb();
		return list;
	}

	public T querySingle(String TableName, String Colums, String Where,
                         String Order, Class<?> c) {
		List<T> list = query(TableName, Colums, Where, Order, c);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 得到创建数据库表语句
	 */
	public List<String> extractStr(String DBName, String TableName) {
		List<String> zdmc = new ArrayList<String>();
		String sql = "SELECT * FROM sqlite_master where  type='table' and tbl_name='"
				+ TableName + "'";
		Resultobj obj = db.query(sql, null);
		if (obj.rowCount() > 0) {
			// 得到创建表的sql语句
			String create_tb_Str = obj.getValue(0, "sql");
			// 截取字符串
			String str1 = create_tb_Str.substring(
					create_tb_Str.indexOf("(") + 1,
					create_tb_Str.lastIndexOf(")"));
			String str2[] = str1.split(",");
			List<String> zdlx = new ArrayList<String>();
			for (String s : str2) {
				// 滤空格
				s = s.trim().split(" ")[0];
				String strs;
				try {
					// 2011-7-28巫作坤修改此处代码，在调试的时候发现有的时候创建语句中字段会没有[]括起来，有时会有，为了适应这两种状态
					// 将原来的以[]为字段名的特征提取方式改成现在这样
					strs = s.replace("[", "").replace("]", "").replace("\"","");
					zdmc.add(strs);
					// zdlx.add(s.split(" ")[1]);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}
		return zdmc;
	}

	/**
	 * 表结构和实体类的反射处理类 目前该方法还存在很多不足之处 <br>
	 * 1、数据库的要求不能为空时实体类却传一个空值需要在这里检测出来<br>
	 * 2、数据项的长度没有完成检测<br>
	 *
	 * @param bean
	 * @param zdmc
	 * @return
	 */
	private ContentValues reflectBean(T bean, List<String> zdmcList) {
		ContentValues cvs = new ContentValues();

		Class<? extends Object> beanClass = bean.getClass();
		Field[] fArr = beanClass.getDeclaredFields();
		for (int i = 0; i < fArr.length; i++) {
			fArr[i].setAccessible(true);
			String fieldName = fArr[i].getName();
			Object fieldValue = null;
			try {
				fieldValue = fArr[i].get(bean);
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			}
			if (fieldValue == null)
				continue;
			if (zdmcList.contains(fieldName)) {
				if (fArr[i].getType() == java.util.Arrays.class) {
					byte[] value = (byte[]) fieldValue;
					cvs.put(fieldName, value);
				} else {
					cvs.put(fieldName, fieldValue.toString());
				}
			}
		}

		return cvs;
	}
}
