package com.xuj.lib;

public class CrcTool {


    public static byte getCrcLow(byte[] body) {
        byte crcLow = getCrc(body)[1];
        return crcLow;
    }

    public static byte[] getCrc(byte[] body) {
        int i = CRC16_XMODEM(body);
        byte[] bytes1 = TextHelper.intToByte2(i);
        return bytes1;
    }


    public static int CRC16_XMODEM(byte[] buffer) {
        int wCRCin = 0x0000; // initial value 65535
        int wCPoly = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)
        for (byte b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        return wCRCin ^= 0x0000;
    }

}
