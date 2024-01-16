package com.example.smartkeycabinet.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        String macAddress = "";
        macAddress = getWifiMacAddress(context);
        if (macAddress.isEmpty()) {
            macAddress = getEthernetMacAddress();
            if (!macAddress.isEmpty()) {
                return macAddress + "(以太网)";
            }
        } else {
            return macAddress + "(WIFI)";
        }
        macAddress = "未获取到该设备MAC地址";
        return macAddress;
    }

    public static String getWifiMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return info.getMacAddress();
            }
        }
        return "";
    }

    @SuppressLint("NewApi")
    public static String getEthernetMacAddress() {
        try {
            String interfaceName = "eth0"; // 或者是其他以太网接口名称
            String filePath = "/sys/class/net/" + interfaceName + "/address";
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            return new String(fileContent, StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            // 处理异常
            return "Unavailable";
        }
    }
}
