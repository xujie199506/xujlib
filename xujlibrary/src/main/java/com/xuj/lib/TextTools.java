package com.xuj.lib;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TextTools {


	/**
	 * 把单个字节转换成二进制字符串
	 */
	public static String byteToBin(byte b) {
		String zero = "00000000";
		String binStr = Integer.toBinaryString(b & 0xFF);
		if(binStr.length() < 8) {
			binStr = zero.substring(0, 8 -binStr.length()) + binStr;
		}
		System.out.println(binStr);
		return binStr;
	}

	/**
	 * 获取字节在内存中某一位的值,采用字符取值方式
	 */
	public static Integer getBitByByteDx(byte b, int index) {
		return  getBitByByte(b,7-index);
	}
	public static Integer getBitByByte(byte b, int index) {
		if(index >= 8) { return null; }
		Integer val = null;
		String binStr = byteToBin(b);
		val = Integer.parseInt(String.valueOf(binStr.charAt(index)));
		return val;
	}

	/**
	 * 获取字节在内存中多位的值,采用字符取值方式(包含endIndex位)
	 */
	public static Integer getBitByByte(byte b, int begIndex, int endIndex) {
		if(begIndex >= 8 || endIndex >= 8 || begIndex >= endIndex) { return null; }
		Integer val = null;
		String binStr = byteToBin(b);
		val = Integer.parseInt(binStr.substring(begIndex, endIndex +1), 2);
		return val;
	}



	public static boolean isEmpty(String str) {
		boolean re = false;

		if (str == null || str.trim().length() == 0) {
			re = true;
		}

		return re;
	}

	public static String clearSpace(String data) {
		String re = "";
		String[] tmp = data.split(" ");
		for (String str : tmp) {
			re += str;
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
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 字节数组转hexStr
	 * @param bArray
	 * @return
	 */

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

	public static String byteToHexStringAddSpace(byte[] bArray) {
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

			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 *hexstr转字节数组
	 */
	public static byte[] hexStringToByteArray(String s) {
		byte[] buf = new byte[s.length() / 2];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) (chr2hex(s.substring(i * 2, i * 2 + 1)) * 0x10
					+ chr2hex(s.substring(i * 2 + 1, i * 2 + 2)));
		}
		return buf;
	}


	public static byte chr2hex(String chr) {
		if (chr.equals("0")) {
			return 0x00;
		} else if (chr.equals("1")) {
			return 0x01;
		} else if (chr.equals("2")) {
			return 0x02;
		} else if (chr.equals("3")) {
			return 0x03;
		} else if (chr.equals("4")) {
			return 0x04;
		} else if (chr.equals("5")) {
			return 0x05;
		} else if (chr.equals("6")) {
			return 0x06;
		} else if (chr.equals("7")) {
			return 0x07;
		} else if (chr.equals("8")) {
			return 0x08;
		} else if (chr.equals("9")) {
			return 0x09;
		} else if (chr.equalsIgnoreCase("A")) {
			return 0x0a;
		} else if (chr.equalsIgnoreCase("B")) {
			return 0x0b;
		} else if (chr.equalsIgnoreCase("C")) {
			return 0x0c;
		} else if (chr.equalsIgnoreCase("D")) {
			return 0x0d;
		} else if (chr.equalsIgnoreCase("E")) {
			return 0x0e;
		} else if (chr.equalsIgnoreCase("F")) {
			return 0x0f;
		}
		return 0x00;
	}


	public synchronized static String removeTail0(String str) {
		if (isEmpty(str)) {
			return str;
		}
		return str.replaceAll("(0){1,}$", "");
	}


	public synchronized static String removeHead0(String str) {
		if (isEmpty(str)) {
			return str;
		}
		return str.replaceFirst("^0+", "");
	}

	public synchronized static String removeHeadTail0(String str) {
		if (isEmpty(str)) {
			return str;
		}
		return str.replaceAll("(0){1,}$", "").replaceFirst("^0+", "");
	}


	public static String padLeft(String str, int totalWidth, String paddingString) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < totalWidth) {
			sb = new StringBuffer();
			sb.append(paddingString).append(str);
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}


	public static String PadRight(String str, int totalWidth, String paddingString) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < totalWidth) {
			sb = new StringBuffer();
			sb.append(str).append(paddingString);// ��(��)��0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}


	public static int byteToInt2(byte[] b) {
		int mask = 0xff;
		int temp = 0;
		int n = 0;
		for (int i = 0; i < b.length; i++) {
			n <<= 8;
			temp = b[i] & mask;
			n |= temp;
		}
		return n;
	}


	public static byte[] intToByte2(int i) {
		byte[] targets = new byte[2];
		targets[1] = (byte) (i & 0xFF);
		targets[0] = (byte) (i >> 8 & 0xFF);
		return targets;
	}


	public static byte[] intToByte4(int i) {
		byte[] targets = new byte[4];
		targets[3] = (byte) (i & 0xFF);
		targets[2] = (byte) (i >> 8 & 0xFF);
		targets[1] = (byte) (i >> 16 & 0xFF);
		targets[0] = (byte) (i >> 24 & 0xFF);
		return targets;
	}


	public static byte[] longToByte8(long lo) {
		byte[] targets = new byte[8];
		for (int i = 0; i < 8; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((lo >>> offset) & 0xFF);
		}
		return targets;
	}

	public static byte[] unsignedShortToByte2(int s) {
		byte[] targets = new byte[2];
		targets[0] = (byte) (s >> 8 & 0xFF);
		targets[1] = (byte) (s & 0xFF);
		return targets;
	}


	public static int byte2ToUnsignedShort(byte[] bytes) {
		return byte2ToUnsignedShort(bytes, 0);
	}


	public static int byte2ToUnsignedShort(byte[] bytes, int off) {
		int high = bytes[off];
		int low = bytes[off + 1];
		return (high << 8 & 0xFF00) | (low & 0xFF);
	}


	public static int byte4ToInt(byte[] bytes, int off) {
		int b0 = bytes[off] & 0xFF;
		int b1 = bytes[off + 1] & 0xFF;
		int b2 = bytes[off + 2] & 0xFF;
		int b3 = bytes[off + 3] & 0xFF;
		return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
	}


	public static short byte2ToShort(byte[] bytes) {
		int b0 = bytes[0] & 0xFF;
		int b1 = bytes[1] & 0xFF;
		return (short) ((b0 << 8) | b1);
	}

	public  static boolean IsHex(String str) {
		boolean b = false;
		char[] c = str.toUpperCase().toCharArray();
		for (int i = 0; i < c.length; i++) {
			if ((c[i] >= '0' && c[i] <= '9') || (c[i] >= 'A' && c[i] <= 'F')) {
				b = true;
			} else {
				b = false;
				break;
			}
		}
		return b;
	}


	public static String bytes2Str(byte... data) {
		StringBuffer sb = new StringBuffer();
		for (byte b : data) {
			String tem = Integer.toHexString(b & 0xff).toUpperCase();
			sb.append(" 0x");
			sb.append(tem.length() < 2 ? "0" + tem : tem);
		}
		return sb.toString();
	}
	public static String bytesStr(byte... data) {
		StringBuffer sb = new StringBuffer();
		for (byte b : data) {
			String tem = Integer.toHexString(b & 0xff).toUpperCase();
			sb.append(tem.length() < 2 ? "0" + tem : tem);
		}
		return sb.toString();
	}
	/**
	 * byte[] 转int
	 *
	 * @param data 二进制数组
	 * @return integer
	 */
	public static int bytesToInteger(byte... data) {
		int value = 0;
		for (int i = Math.max(data.length - 4, 0); i < data.length; i++) {
			int w = ((data.length - i - 1) * 8);
			value = value | ((data[i] & 0xFF) << w);
		}
		return value;
	}

	public static int bytesToInteger16(byte... data) {
		int value = data[1] | ((data[0] & 0xFF) << 8);
		return value;
	}

	public static int getIntFromBytes(byte high_h, byte high_l, byte low_h, byte low_l) {
		return (high_h & 0xff) << 24 | (high_l & 0xff) << 16 | (low_h & 0xff) << 8 | low_l & 0xff;
	}

	public static byte[] integerTobytes(int arg) {
		byte[] res = new byte[4];
		res[0] = (byte) (0xff & arg);
		res[1] = (byte) ((0xff00 & arg) >> 8);
		res[2] = (byte) ((0xff0000 & arg) >> 16);
		res[3] = (byte) ((0xff000000 & arg) >> 24);
		return res;
	}

	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		//由高位到低位
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;//往高位游
		}
		return value;
	}


	public static short byte2short(byte data, byte data2) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(data);
		bb.put(data2);
		short shortVal = bb.getShort(0);
		return shortVal;
	}

	public static byte[] float2Byte(float myFloat) {
		int bits = Float.floatToIntBits(myFloat);
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (bits & 0xff);
		bytes[1] = (byte) ((bits >> 8) & 0xff);
		bytes[2] = (byte) ((bits >> 16) & 0xff);
		bytes[3] = (byte) ((bits >> 24) & 0xff);
		return bytes;
	}

	public static float byte2float(byte... data) {
		float myfloatvalue = ByteBuffer.wrap(data).getFloat();
		return myfloatvalue;
	}



}
