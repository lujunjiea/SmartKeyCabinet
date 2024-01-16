package com.example.smartkeycabinet.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

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

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return info.getMacAddress();
            }
        }
        return "Unavailable";
    }
}
