package com.xuj.lib.db.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Texts {
	/** DEFAULT_CHARSET Ĭ���ַ��� */
	public static String DEFAULT_CHARSET = "UTF-8";

	public static String subStringTail(String data, String headInfo) {
		String re = "";
		int index = data.indexOf(headInfo);
		re = data.substring(index + headInfo.length());
		return re;
	}

	/**
	 * ���ַ�������ת��ΪArrayList.<br>
	 * <br>
	 * 
	 * @param datas
	 * @return
	 * @Description   ::��˳::�����˷���</br>
	 */
	public static ArrayList<String> strings2ArrayList(String[] datas) {
		ArrayList<String> re = new ArrayList<String>();

		for (String data : datas) {
			re.add(data);
		}

		return re;
	}

	/**
	 * ��ȡ�ַ���ͷ��.<br>
	 * <br>
	 * 
	 * @param str
	 *            �ַ���
	 * @param flag
	 *            �����ʶ
	 * @param startFlag
	 *            �ӵڼ�����ʶ��ʼ
	 * @return ��ȡ���
	 * @Description 2013-6-8::��˳::�����˷���</br>
	 */
	public static String subStringHead(String str, char flag, int startFlag) {
		String re = "";
		int titlePosition = str.indexOf(flag);
		if (titlePosition >= 0) {
			for (int i = 0; i < startFlag - 1; i++) {
				titlePosition = str.indexOf(flag, titlePosition + 1);
			}
			re = str.substring(0, titlePosition);
		} else {
			re = str;
		}

		return re;
	}

	/**
	 * ��ȡ�ַ���ͷ��.<br>
	 * <br>
	 * 
	 * @param str
	 *            �ַ���
	 * @param flag
	 *            �����ʶ
	 * @return ��ȡ���
	 * @Description 2013-6-8::��˳::�����˷���</br>
	 */
	public static String subStringHead(String str, char flag) {
		return subStringHead(str, flag, 1);
	}

	/**
	 * ��ȡ�ַ���β��.<br>
	 * <br>
	 * 
	 * @param str
	 *            ����ȡ�ַ���
	 * @param flag
	 *            �����־
	 * @param startFlag
	 *            �ӵڼ�����ʶ��ʼ
	 * @return
	 * @Description 2013-6-8::��˳::�����˷���</br>
	 */
	public static String subStringTail(String str, char flag, int startFlag) {
		String re = "";
		int titlePosition = str.lastIndexOf(flag);
		if (titlePosition >= 0) {
			for (int i = 0; i < startFlag - 1; i++) {
				titlePosition = str.lastIndexOf(flag, titlePosition - 1);
			}
			re = str.substring(titlePosition + 1);
		} else {
			re = str;
		}

		return re;
	}

	/**
	 * ��ȡ�ַ���β��.<br>
	 * <br>
	 * 
	 * @param str
	 *            ����ȡ�ַ���
	 * @param flag
	 *            �����־
	 * @return
	 * @Description 2013-6-8::��˳::�����˷���</br>
	 */
	public static String subStringTail(String str, char flag) {
		return subStringTail(str, flag, 1);
	}

	/**
	 * �ֽ�����ת��Ϊ�ַ���.<br>
	 * <br>
	 * 
	 * @param b
	 * @param start
	 * @param end
	 * @return
	 * @Description 2013-6-7::��˳::�����˷���</br>
	 */
	public static String byte2String(byte[] b, int start, int end) {
		String re = "";
		try {
			re = new String(b, start, end - start, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return re;
	}

	/**
	 * �ַ���ת��Ϊ����.<br>
	 * <br>
	 * 
	 * @param data
	 * @return
	 * @Description 2013-6-8::��˳::�����˷���</br>
	 */
	public static int String2int(String data) {
		int re = -1;

		try {
			re = Integer.parseInt(data);
		} catch (Exception e) {
		}

		return re;
	}

	public static byte[] hexString2Bytes(String src) {
		byte[] tmp = src.getBytes();
		int len = tmp.length;
		if (len % 2 != 2) {
			len++;
		}
		byte[] ret = new byte[len / 2];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static String byteToHexString(byte[] bArray) {
		if (bArray == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length + bArray.length / 2);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

}
