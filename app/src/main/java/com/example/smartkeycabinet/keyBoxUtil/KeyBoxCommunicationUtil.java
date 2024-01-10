package com.example.smartkeycabinet.keyBoxUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/3/25.
 * 钥匙柜通讯工具类
 */

public class KeyBoxCommunicationUtil {
    /**
     * 检测箱门 状态
     * Check box door status
     */
    public static final String RESULT_OPEN_DOOR = "00000000";
    public static final String RESULT_CLOSE_DOOR = "FFFFFFFF";
    /**
     * 检测小箱门 状态
     * Check box door status
     */
    public static final String STATE_SMALL_DOOR_OPEN = "FFFFFFFF";
    public static final String STATE_SMALL_DOOR_CLOSE = "00000000";
    /**
     * 微动开关 状态,是否有钥匙栓
     * Micro switch status, is there a key pin?
     */
    public static final String RESULT_KEY_BOLT = "00000000";
    public static final String RESULT_NOT_KEY_BOLT = "FFFFFFFF";
    /**
     * 微动开关 + 读卡，是否有钥匙栓或异物
     * Micro switch + read card, is there a key pin or foreign body?
     */
    public static final String MICRO_SWITCHAND_READ_CARD_HAVE_FOREIGN_BODY = "00000000";
    public static final String MICRO_SWITCHAND_READ_CARD_NOT_KEY = "FFFFFFFF";
}
