package com.example.smartkeycabinet.keyBoxUtil;


public enum CommandType {
    /**
     * 检测箱门状态
     */
    CHECK_DOOR_STATUS,

    /**
     * 开门
     */
    OPEN_DOOR,

    /**
     * 开小锁（门内小磁铁）
     */
    OPEN_DOOR_MAGNET,

    /**
     * 关小锁（门内小磁铁）
     */
    CLOSE_DOOR_MAGNET,

    /**
     * 门内小锁状态检测（是否有钥匙）
     */
    CHECK_DOOR_MAGNET_STATUS,

    /**
     * 蓝灯开
     */
    BLUE_LIGHT_ON,

    /**
     * 蓝灯关
     */
    BLUE_LIGHT_OFF,

    /**
     * 红灯开
     */
    RED_LIGHT_ON,

    /**
     * 红灯关
     */
    RED_LIGHT_OFF
}
