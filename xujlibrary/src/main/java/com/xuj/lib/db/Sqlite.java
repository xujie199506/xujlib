package com.xuj.lib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.xuj.lib.db.utils.FileUtils;
import com.xuj.lib.db.utils.Path;
import com.xuj.lib.db.utils.Preferences;
import com.xuj.lib.db.utils.Texts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class Sqlite {
    public static int DB_VERSION = 1;
    public static String APP = "com";
    public final static int DB_DEFAULT_VERSION = 0;
    public final static String DB_CURRENT_VERSION = "DB_CURRENT_VERSION";
    /**
     * TID 表ID字段名
     */
    public static String DB_TID = "TID";

    public static class TABLE_NAMES {
        public final static String S_CODECLASS = "S_CODECLASS";
        public final static String S_CODE_OTHER = "S_CODE_OTHER";
        public final static String BACK_SEND = "BACK_SEND";
    }

    /**
     * COMMON 通用数据库名称
     */
    public static String DB_COMMON = "common.db";
    /**
     * TAG
     */
    private final String TAG = Sqlite.class.getName();
    /**
     * context 上下文
     */
    private Context context;
    /**
     * dbName 数据库名
     */
    private String dbName;
    /**
     * db 数据库对象
     */
    private SQLiteDatabase db;
    private boolean transactioning;
    private static Sqlite sqlite;

    public Sqlite(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
    }

    public static Sqlite getInstance(Context context) {
        if (sqlite == null) {
            sqlite = new Sqlite(context, Sqlite.DB_COMMON);
        }

        return sqlite;
    }

    public static Sqlite getInstance(Context context, String dbName) {
        if (sqlite == null) {
            sqlite = new Sqlite(context, dbName);
        }

        return sqlite;
    }

    /**
     * 数据库对象是否可用判断.<br>
     * <br>
     *
     * @return
     * @Description ::创建此方法</br>
     */
    private boolean isDbDisible() {
        return (db == null || !db.isOpen());
    }

    /**
     * 打开数据库.<br>
     * <br>
     *
     * @Description 2013-6-9::   ::创建此方法<br>
     */
    private void openDb() {
        closeDb();
        try {
            db = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE,
                    null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 关闭数据库.<br>
     * <br>
     *
     * @Description 2013-6-9::   ::创建此方法<br>
     */
    public void closeDb() {
        try {
            if (!isDbDisible()) {
                if (!transactioning) {
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据.<br>
     * <br>
     *
     * @param tbName 表名
     * @param kv     键值集合
     * @return 影响行数
     * @Description 2013-6-9::   ::创建此方法<br>
     */

    public int insert(String tbName, ContentValues kv) {
        int re = -1;
        if (isDbDisible()) {
            openDb();
        }
        re = (int) db.insert(tbName, "", kv);
        closeDb();
        return re;
    }

    /**
     * 删除数据.<br>
     * <br>
     *
     * @param tbName 表名
     * @param where  条件
     * @return 影响行数
     * @Description ::创建此方法<br>
     */

    public int delete(String tbName, String where, String[] whereArgs) {
        int re = -1;
        if (isDbDisible()) {
            openDb();
        }

        re = db.delete(tbName, where, whereArgs);
        closeDb();
        return re;
    }

    /**
     * 更新数据.<br>
     * <br>
     *
     * @param tbName 表名
     * @param kv     键值
     * @param where  条件
     * @return 影响行数
     * @Description ::创建此方法<br>
     */

    public int update(String tbName, ContentValues kv, String where,
                      String[] whereArgs) {
        int re = -1;
        if (isDbDisible()) {
            openDb();
        }
        re = db.update(tbName, kv, where, whereArgs);
        closeDb();
        return re;
    }

    /**
     * 获取查询结果.<br>
     * <br>
     *
     * @param sql
     * @param selectionArgs
     * @return
     * @Description ::创建此方法</br>
     */
    public Resultobj query(String sql, String[] selectionArgs) {
        Resultobj re = new Resultobj();
        if (isDbDisible()) {
            openDb();
        }
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor != null) {
            // 获取标题集合
            re.setTitles(Texts.strings2ArrayList(cursor.getColumnNames()));
            // 获取总列数
            int columCount = re.columCount();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String[] cols = new String[columCount];
                for (int i = 0; i < columCount; i++) {
                    cols[i] = cursor.getString(i);
                }
                re.add(cols);
                cursor.moveToNext();
            }
            cursor.close();
        }
        closeDb();
        return re;
    }

    /**
     * 获取查询结果.<br>
     * <br>
     *
     * @param sql
     * @return
     * @Description ::创建此方法</br>
     */
    public Resultobj query(String sql) {
        return query(sql, null);
    }

    /**
     * 数据查询.<br>
     * <br>
     *
     * @param tbName
     * @param colums
     * @param where
     * @param whereArgs
     * @param orderBy
     * @param limit
     * @return
     * @Description 2013-7-10::   ::创建此方法<br>
     */
    public Cursor query(String tbName, String[] colums, String where,
                        String[] whereArgs, String orderBy, String limit) {

        if (isDbDisible()) {
            openDb();
        }
        Cursor cursor = null;
        cursor = db.query(tbName, colums, where, whereArgs, null, null,
                orderBy, limit);
        return cursor;
    }

    /**
     * 获取分页查询结果.<br>
     * <br>
     *
     * @param sql
     * @param selectionArgs
     * @param pageNum       ，从1开始
     * @param pageCount
     * @return
     * @Description ::创建此方法<br>
     */
    public Resultobj queryForPage(String sql, String[] selectionArgs,
                                  int pageNum, int pageCount) {
        // 拼装分页语句
        int offset = 0;
        offset = (pageNum - 1) * pageCount;
        sql = sql + " limit " + pageCount + " offset " + offset;
        return query(sql, selectionArgs);
    }

    /**
     * 获取分页查询结果.<br>
     * <br>
     *
     * @param sql
     * @param pageNum
     * @param pageCount
     * @return
     * @Description ::创建此方法<br>
     */
    public Resultobj queryForPage(String sql, int pageNum, int pageCount) {
        return queryForPage(sql, null, pageNum, pageCount);
    }

    /**
     * 查询数据.<br>
     * <br>
     *
     * @param tbName
     * @param colums
     * @param where
     * @param whereArgs
     * @param orderBy
     * @return
     * @Description ::创建此方法<br>
     */
    public Cursor query(String tbName, String[] colums, String where,
                        String[] whereArgs, String orderBy) {
        if (isDbDisible()) {
            openDb();
        }
        Cursor cursor = null;
        cursor = db
                .query(tbName, colums, where, whereArgs, null, null, orderBy);
        return cursor;
    }

    /**
     * 开启事务.<br>
     * <br>
     *
     * @Description ::创建此方法<br>
     */
    public void beginTransaction() {
        if (isDbDisible()) {
            openDb();
        }
        if (db != null) {
            db.beginTransaction();
            transactioning = true;
        }
    }

    /**
     * 关闭事务.<br>
     * <br>
     *
     * @Description ::创建此方法<br>
     */
    public void endTransaction() {
        try {
            if (db != null) {
                db.endTransaction();
                if (!isDbDisible()) {
                    db.close();
                }
                transactioning = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务.<br>
     * <br>
     *
     * @Description ::创建此方法<br>
     */
    public void commit() {
        if (db != null) {
            db.setTransactionSuccessful();
        }
        endTransaction();
    }

    /**
     * 回滚事务.<br>
     * <br>
     *
     * @Description ::创建此方法<br>
     */
    public void rollback() {
        endTransaction();
    }

    /**
     * 执行查询之外的sql语句.<br>
     * <br>
     *
     * @param sql
     * @param sqlArgs
     * @return
     * @Description ::创建此方法<br>
     */

    public boolean exeSql(String sql, String[] sqlArgs) {
        if (isDbDisible()) {
            openDb();
        }
        boolean re = true;
        try {
            if (sqlArgs == null) {
                db.execSQL(sql);
            } else {
                db.execSQL(sql, sqlArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re = false;
        }
        closeDb();
        return re;
    }

    /**
     * 执行查询之外的sql语句.<br>
     * <br>
     *
     * @param sql
     * @return
     * @Description ::创建此方法<br>
     */

    public boolean exeSql(String sql) {
        return exeSql(sql, null);
    }

    /**
     * 获取查询总行数.<br>
     * <br>
     *
     * @param sql
     * @param selectionArg
     * @return
     * @Description ::创建此方法<br>
     */
    public int queryRowCount(String sql, String[] selectionArg) {
        if (isDbDisible()) {
            openDb();
        }
        int re = 0;

        // 封装sql语句
        sql = "select count(1) from (" + sql + ") A";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, selectionArg);
            if (cursor != null) {
                cursor.moveToFirst();
                re = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        closeDb();

        return re;
    }

    /**
     * 获取查询总行数.<br>
     * <br>
     *
     * @param sql
     * @return
     * @Description ::创建此方法<br>
     */
    public int queryRowCount(String sql) {
        return queryRowCount(sql, null);
    }

    /**
     * 恢复数据库.<br>
     * <br>
     *
     * @return
     * @Description 2013-6-20::   ::创建此方法</br>
     */

    public boolean recover() {
        boolean re = false;

        int dbCurrentVersion = Preferences.getValue(context,
                DB_CURRENT_VERSION, DB_DEFAULT_VERSION);
        if (dbCurrentVersion != DB_VERSION) {
            // 在已安装程序数据库版本和当前安装程序数据库版本不一致情况下启动数据库恢复功能
            if (copyDataBase(DB_COMMON)) {
                Preferences.setValue(context, DB_CURRENT_VERSION, DB_VERSION);
            }
        }
        recoverFromSdcard();

        return re;
    }

    private boolean copyDataBase(String dbName) {
        boolean re = true;
        try {
            SQLiteDatabase db = context.openOrCreateDatabase(dbName,
                    Context.MODE_PRIVATE, null);
            if (db != null) {
                db.close();
            }

            String dbPath = Path.getDbPath(context);
            InputStream myInput = context.getAssets().open(dbName);
            String outFileName = dbPath + "/" + dbName;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
            re = false;
        }
        return re;
    }

    public boolean recoverFromSdcard() {
        boolean re = false;

        try {
            if (Path.isSdCardExist()) {
                String bkPath = Path.recoverPath();
                File bkFile = new File(bkPath);
                if (!bkFile.exists()) {
                    bkFile.mkdirs();
                }

                String dbPath = Path.getDbPath(context);

                String[] fileNames = new File(bkPath).list();
                for (int i = 0; i < fileNames.length; i++) {
                    String fileName = fileNames[i];
                    if (fileName.endsWith(".db") || fileName.endsWith(".xml")) {
                        boolean bo = FileUtils.copy(bkPath + "/" + fileName,
                                dbPath + "/" + fileName);
                        if (bo) {
                            File file = new File(bkPath + "/" + fileName);
                            if (file != null) {
                                file.delete();
                            }
                        }
                    }
                }

                re = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return re;
    }

    /**
     * 备份数据库文件.<br>
     * <br>
     *
     * @return
     * @Description 2013-6-29::   ::创建此方法</br>
     */
    public boolean backup() {
        return backup(context);
    }

    /**
     * 备份数据库文件.<br>
     * <br>
     *
     * @param context
     * @return
     * @Description 2013-6-29::   ::创建此方法</br>
     */
    private boolean backup(Context context) {
        boolean re = false;
        try {
            if (Path.isSdCardExist()) {
                String bkPath = Path.backupPath();
                File bkFile = new File(bkPath);
                if (!bkFile.exists()) {
                    bkFile.mkdirs();
                }

                String dbPath = Path.getDbPath(context);
                String[] fileNames = new File(dbPath).list();
                if (fileNames != null) {
                    for (int i = 0; i < fileNames.length; i++) {
                        String fileName = fileNames[i];
                        if (fileName.endsWith(".db"))
                            FileUtils
                                    .copy(context.getDatabasePath(fileName)
                                            .getAbsolutePath(), bkPath + "/"
                                            + fileName);
                    }
                }

                re = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return re;
    }

    /**
     * 检查表是否存在.<br>
     * <br>
     *
     * @param tbName
     * @return
     * @Description 2013-6-29::   ::创建此方法<br>
     */
    public boolean tableIsExist(String tbName) {

        boolean result = false;
        if (tbName == null) {
            return false;
        }
        try {
            tbName = tbName.trim();
            String sql = "select 1 from Sqlite_master  where (type ='table' or type = 'view') and (name ='"
                    + tbName.toLowerCase()
                    + "' or name = '"
                    + tbName.toUpperCase() + "')";
            int re = queryRowCount(sql);
            if (re > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean tableIsExist(String dbName, String tbName) {
        boolean result = false;
        if (tbName == null) {
            return false;
        }
        try {
            tbName = tbName.trim();
            Sqlite db = Sqlite.getInstance(context, dbName);
            String sql = "select 1 from Sqlite_master  where (type ='table' or type = 'view') and (name ='"
                    + tbName.toLowerCase()
                    + "' or name = '"
                    + tbName.toUpperCase() + "')";
            int re = db.queryRowCount(sql);
            if (re > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建表.<br>
     * <br>
     *
     * @param tbName
     * @param fields
     * @return
     * @Description 2013-6-29::   ::创建此方法<br>
     */
    public boolean createTable(String tbName, ArrayList<String> fields) {
        boolean re = true;
        try {
            String sql = "CREATE TABLE if not exists " + tbName + "("; // +
            // DB_TID
            // +
            // " INTEGER PRIMARY KEY AUTOINCREMENT,";
            int size = fields.size() - 1;
            int i = 0;
            for (String field : fields) {
                sql += field;
                if (i < size) {
                    sql += ",";
                }
                i++;
            }
            sql += ")";

            if (!tableIsExist(tbName)) {
                exeSql(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            re = false;
        }

        return re;
    }

    /**
     * 唯一数据插入.<br>
     * <br>
     *
     * @param tbName
     * @param kv
     * @param idName
     * @return
     * @Description 2013-6-29::   ::创建此方法<br>
     */
    public int insert(String tbName, ContentValues kv, String idName) {
        int re = 0;

        int rowCount = queryRowCount("select 1 from " + tbName + " where "
                + idName + "='" + kv.getAsString(idName) + "'");
        boolean isInsert = (rowCount == 0) ? true : false;
        if (isInsert) {
            insert(tbName, kv);
        }

        return re;
    }

    /**
     * .<br>
     * <br>
     *
     * @param tbName
     * @param kv
     * @param where
     * @param whereArgs
     * @return
     * @Description 2013-7-10::   ::创建此方法<br>
     */
    public int updateOrInsert(String tbName, ContentValues kv, String where,
                              String[] whereArgs) {
        int re = 0;
        if (!TextUtils.isEmpty(where)) {
            int rowCount = queryRowCount("select 1 from " + tbName + " where "
                    + where);
            boolean isInsert = (rowCount == 0) ? true : false;
            if (isInsert) {
                insert(tbName, kv);
            } else {
                update(tbName, kv, where, whereArgs);
            }
        }
        return re;
    }
}
