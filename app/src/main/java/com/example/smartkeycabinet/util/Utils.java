package com.example.smartkeycabinet.util;

public class Utils {
    /**
     * 解析byte数组
     *
     * @param crc
     * @return
     */
    public static String toHexString(byte[] crc) {
        StringBuilder result = new StringBuilder();
        if (crc != null) {
            int length = crc.length;
            int i = 0;
            while (i < length) {
                String hexString = Integer.toHexString(crc[i] < 0 ? crc[i] + 256 : crc[i]);
                result.append(hexString.length() == 1 ? "0" + hexString : hexString).append(" ");
                i++;
            }
            return result.toString();
        }
        return "";
    }
}
