package com.example.smartkeycabinet.keyBoxUtil;

/**
 * 钥匙柜通讯类型
 */
public interface KeyBoxOperateType {

    /**
     * 快速检测
     * 逐位返回每个钥匙的状态
     * F2：有钥匙栓 ； F1:无钥匙栓
     */
    int fastDetection= 0;
    /**
     * 钥匙节点测试
     */
    int testKey = 16;
    /**
     * 蓝灯开
     * Blue light on
     */
    int blueLightOpen = 17;
    /**
     * 蓝灯关
     * Blue light off
     */
    int blueLightClose = 18;
    /**
     * 绿灯开
     * Green light on
     */
    int greenLightOpen = 30;
    /**
     * 绿灯开灯关
     * Green light off
     */
    int geenLightClose = 31;
    /**
     * 电磁锁,锁定
     * electromagnetic Lock
     */
    int electromagneticLock = 20;
    /**
     * 电磁锁，解锁（钥匙可取）
     * electromagnetic Unlock（The key is advisable）
     */
    int electromagneticUnlock = 19;
    /**
     * 读卡并返回卡号
     * Read the card and return the card number
     */
    int readCard = 21;
    /**
     * 读节点编号
     * Read the card and return the card number
     */
    int readNodeNo = 255;
    /**
     * 微触开关检测
     * Micro touch switch detection
     */
    int testOnlineButNotReadCard = 22;
    /**
     * 微触开关检测，并读取卡号
     * Micro touch switch detect and read card number
     */
    int testOnlineAndReadCard = 23;
    /**
     *  关闭箱门-自动门
     *  close the box door
     */
    int closeDoorAuto = 36;
    /**
     * 红灯开
     * Red light on
     */
    int redLightOpen = 25;
    /**
     * 红灯关
     * Red light off
     */
    int redLightClose = 26;
    /**
     * 打开箱门
     * Open the box door
     */
    int doorOpen = 35;
    /**
     * 意林锁的开关门命令
     */
    int doorOpenUrgent = 32;

    int doorClose = 33;
    /**
     * 检测钥匙柜门状态
     * Check the status of key cabinet door
     */
    int testDoorState = 34;
    /**
     * 打开小箱门
     * Open the box small door
     */
    int smallDoorOpen = 28;
    /**
     * 关闭小箱门（自动门）
     * clsoe the box small door
     */
    int smallDoorClose = 39;
    /**
     * 检测钥匙柜门状态
     * Check the status of key cabinet small door
     */
    int testSmallDoorState = 29;

    /**
     * 读输入开关量状态--衬板监测
     *
     */
    int nodeGroupMonitor = 38;

    /**
     * 读取钥匙箱编号
     */
    int readBoxId = 64;
    /**
     * 写入钥匙箱编号,前两字节
     */
    int writeBoxIdFirstTwo = 65;
    /**
     * 写入钥匙箱编号,后两字节
     */
    int writeBoxIdNextTwo= 66;

    /**
     * 读取钥匙箱编号 9-16
     */
    int readBoxId_9_16 = 67;
    /**
     * 写入钥匙箱编号,前两字节
     */
    int writeBoxIdFirstTwo_9_12 = 68;
    /**
     * 写入钥匙箱编号,后两字节
     */
    int writeBoxIdNextTwo_13_16 = 69;
    /**
     * 主动上报
     */
    int activeEscalation = 160;
    /**
     * 释放钥匙
     */
    int takeKey = 161;
    /**
     * 释放错误钥匙
     */
    int takeErrorKey = 162;


    /**========================行业H30协议 start ==============================**/
//    /**
//     * 蓝灯开
//     * Blue light on
//     */
//    int blueLightOpen = 0x61;
//    /**
//     * 蓝灯关
//     * Blue light off
//     */
//    int blueLightClose = 0x62;
//    /**
//     * 电磁锁,锁定
//     * electromagnetic Lock
//     */
//    int electromagneticLock = 0x64;
//    /**
//     * 电磁锁，解锁（钥匙可取）
//     * electromagnetic Unlock（The key is advisable）
//     */
//    int electromagneticUnlock = 0x63;
//    /**
//     * 读卡并返回卡号
//     * Read the card and return the card number
//     */
//    int readCard = 0x65;
//    /**
//     * 微触开关检测
//     * Micro touch switch detection
//     */
//    int testOnlineButNotReadCard = 0x66;
//    /**
//     * 微触开关检测，并读取卡号
//     * Micro touch switch detect and read card number
//     */
//    int testOnlineAndReadCard = 0x67;
//    /**
//     * 红灯开
//     * Red light on
//     */
//    int redLightOpen = 0x69;
//    /**
//     * 红灯关
//     * Red light off
//     */
//    int redLightClose = 0x6A;
//    /**
//     *  打开箱门（阴极锁）
//     *  Open the box door
//     */
//    int doorOpenUrgent=0x70;
//
//
//    /**
//     *  关闭箱门  (阴极锁)
//     *  Open the box door
//     */
//    int doorClose=0x71;
//    /**
//     * 检测钥匙柜门状态
//     * Check the status of key cabinet door
//     */
//    int testDoorState = 0x72;
//    /**
//     * 打开箱门
//     * Open the box door
//     */
//    int doorOpen = 0x73;
//    /**
//     * 设置钥匙节点编号（无返回值）
//     */
////    int setKeyAddressNo = "75";
//    /**
//     * 带小门的开门
//     */
//    int smallDoorOpen = 0x4C;
//    /**
//     * 带小门的检测箱门状态
//     */
//    int testSmallDoorState = 0x4D;
//    /**
//     * 打开外部指示灯命令号：0x4E
//     */
//    int openExternalLight= 0x4E;
//    /**
//     * 关闭外部指示灯命令号:0x4F
//     */
//    int closeExternalLight = 0x4F;
//    /**
//     * 多节点开关????
//     */
//    int multinodeSwitch = 0x60;
//    /**
//     * 全部节点复位???
//     */
//    int resetAll = 0x01;
//    /**
//     *  读取箱ID
//     */
//    int readBoxId=0xE0;
//    /**
//     *  写箱ID前2字节
//     */
//    int writeBoxIdFirstTwo=0xE1;
//    /**
//     *  写箱ID后2字节
//     */
//    int writeBoxIdNextTwo=0xE2;
    /**========================行业H30协议 end ==============================**/

    /**
     * 设置钥匙节点通讯编号
     */
    int SET_KEY_NODE_COMMUNICATION_NUMBER = 37;

    int SET_KEY_NODE_COMMUNICATION_NUMBER_1B = 27;

    /** ========================孙工主板特有命令 start ============================== **/
    /**
     * 测试:广播通知硬件获取卡号
     */
    int radio_key_card = 161;
    /**
     * 测试:广播获取对应钥匙节点的卡号
     */
    int radio_read_key_card = 162;
    /**
     * 报警检测
     */
    int alarm_detection = 243;
    /** ========================孙工主板特有命令 end ============================== **/
}