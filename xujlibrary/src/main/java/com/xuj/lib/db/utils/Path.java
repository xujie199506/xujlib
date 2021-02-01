package com.xuj.lib.db.utils;

import android.content.Context;
import android.os.Environment;

import com.xuj.lib.db.Sqlite;

import java.io.File;

public class Path {
	public static final String BACK_SLASH = "/";
	public static final String INTERNAL_SD_CARD_PATH = "/sdcard";
	/** BACKUP ���� */
	public static final String BACKUP = "backup";
	/** RECOVER �ָ� */
	public static final String RECOVER = "recover";
	/** PICTURE ͼƬ */
	public static final String PICTURE = "picture";

	public static String getDbPathWithSeparators(Context context) {
		String dbPath = context.getDatabasePath(Sqlite.DB_COMMON).getAbsolutePath();
		if (dbPath == null) {
			dbPath = "";
		}
		return dbPath;
	}

	public static String getDbPath(Context context) {
		String dbPath = getDbPathWithSeparators(context);

		int idx = dbPath.lastIndexOf(BACK_SLASH);
		if (idx <= 0) {
			dbPath = "";
		} else {
			dbPath = dbPath.substring(0, idx);
		}

		return dbPath;
	}

	/**
	 * ��ȡ�洢��·��.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String getSdCardPath() {
		return getSdCardPath(true);
	}

	/**
	 * ��ȡ�洢��·�����ָ�����׺.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String getSdCardPathWithSeparators() {
		return getSdCardPath() + BACK_SLASH;
	}

	/**
	 * ��ȡ�洢��·��.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String getSdCardPath(boolean useInternalCard) {
		String sdCardPath = INTERNAL_SD_CARD_PATH;

		if ((!useInternalCard || !new File(sdCardPath).exists())
				&& Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		}

		return sdCardPath;
	}

	/**
	 * TF���Ƿ����.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static boolean isSdCardExist() {
		boolean re = false;

		re = (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				|| new File(INTERNAL_SD_CARD_PATH).exists();

		return re;
	}

	/**
	 * ��鲢��ȡ�ָ�·��.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String recoverPath() {
		String path = "";

		path = appPathWithSeparators() + RECOVER;
		FileUtils.pathExists(path);

		return path;
	}

	/**
	 * ��鲢��ȡӦ��·��.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String appPathWithSeparators() {
		return appPath() + BACK_SLASH;
	}

	public static String getDownloadPath() {
		String path = appPathWithSeparators() + "download";
		FileUtils.pathExists(path);
		return path;
	}

	public static String getDownloadPathWithSeparators() {
		return getDownloadPath() + BACK_SLASH;
	}

	/**
	 * ��鲢��ȡӦ��·��.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String appPath() {
		String path = "";
		path = getSdCardPathWithSeparators() + Sqlite.APP;
		FileUtils.pathExists(path);

		return path;
	}

	/**
	 * ��鲢��ȡ����·��.<br>
	 * <br>
	 * 
	 * @return
	 * @Description 2013-7-1::��˳::�����˷���</br>
	 */
	public static String backupPath() {
		String path = "";

		path = appPathWithSeparators() + BACKUP;
		FileUtils.pathExists(path);

		return path;
	}

	public static String picturePathWithSeparators() {

		return picturePath() + BACK_SLASH;
	}

	public static String picturePath() {
		String path = "";
		path = appPathWithSeparators() + PICTURE;
		FileUtils.pathExists(path);
		return path;
	}
}
