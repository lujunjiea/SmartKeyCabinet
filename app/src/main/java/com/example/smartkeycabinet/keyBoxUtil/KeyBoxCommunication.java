package com.example.smartkeycabinet.keyBoxUtil;

import android.util.Log;

/**
 * 钥匙柜通讯类
 */

public class KeyBoxCommunication {

    /**
     * 得到通讯实体类
     */
    public KeyBoxCommunication() {
    }


    /**
     * 字符串左补位
     *
     * @param str1，要补位的字符串
     * @param len，补位长度
     * @return 补位完的String类型字符串
     */
    private String padLeft(String str1, int len) {
        String result = str1;
        for (int i = 0; i < (len - str1.length()); i++) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * 字符串右补位
     *
     * @param str1，要补位的字符串
     * @param len，补位长度
     * @return 补位完的String类型字符串
     */
    private String padRight(String str1, int len) {
        String result = str1;
        for (int i = 0; i < (len - str1.length()); i++) {
            result = result + "0";
        }
        return result;
    }

    /**
     * 返回拆分的字符串
     *
     * @param str1 要拆分的字符串
     * @return 拆分后的字符串
     */
    private String splitString(String str1) {
        String result = "";
        for (int i = 0; i < str1.length(); i++) {
            result += "3" + str1.substring(i, i + 1);
        }
        return result;
    }

    /**
     * 返回拆分前的原始数据
     *
     * @param data 拆分后的数组
     * @return 拆分前的数组
     */
    private int[] mergeIntegerArray(int[] data) {
        int result[] = null;
        String hexDataString = "";
        String mergeDataString = "";
        for (int i = 0; i < data.length; i++) {
            hexDataString += padLeft(Integer.toHexString(data[i]), 2);
        }
//        Log.e("通讯--","hexDataString==" + hexDataString);
        for (int i = 0; i < hexDataString.length(); i = i + 2) {
            mergeDataString += hexDataString.substring(i + 1, i + 2);
        }
//        Log.e("通讯--","mergeDataString==" + mergeDataString);
        int i_Pos = 0;
        result = new int[mergeDataString.length() / 2];
        for (int i = 0; i < mergeDataString.length(); i = i + 2) {
            result[i_Pos] = Integer.parseInt(mergeDataString.substring(i, i + 2), 16);
            i_Pos++;
        }
        return result;
    }

    /**
     * 对收到的数据进行校验
     *
     * @return 成功返回true，失败返回false
     */
    public boolean dataCheck(byte[] _bytesGet) {
        //接受：0x01 + 2箱地址 + 2钥匙地址 + 2命令号 + 8卡号 +2校验 + 0x03
        int[] bytesReceives = new int[_bytesGet.length];
        for (int i = 0; i < _bytesGet.length; i++) {
            bytesReceives[i] = (int) _bytesGet[i];
        }
        int[] body = new int[bytesReceives.length - 2];
        int i_Pos = 0;
        if (bytesReceives[0]!=0x01 || bytesReceives[bytesReceives.length-1] != 0x03) {
            Log.e("通讯--","dataCheck==接收原始数据--头尾不一致");
            return false;
        }
        //去掉头0x01,尾0x03
        for (int i = 1; i < bytesReceives.length - 1; i++) {
            body[i_Pos] = bytesReceives[i];
            i_Pos++;
        }
        //获得合并后的数组
        int[] mergeBody = mergeIntegerArray(body);
        //钥匙柜回复的校验值
        int checkReceive = mergeBody[mergeBody.length - 1];
        //得到回复的内容（去掉校验值）
        int[] data = new int[mergeBody.length - 1];
        for (int i = 0; i < mergeBody.length - 1; i++) {
            data[i] = mergeBody[i];
        }
        //对回复的内容进行校验
        int checkValue = Acrc8(data, data.length);
        if (checkValue == checkReceive) {
            String command = Integer.toHexString(data[2] & 0xFF).toUpperCase();
            String keyNo = Integer.toHexString(data[1] & 0xFF).toUpperCase();
//            commandLog ="接收-节点号=" + keyNo +"-命令号--" + command + "-校验通过-" + CharConversionUtil.byte2HexString(_bytesGet);
            //校验成功，保存4字节卡号，以便是读卡操作进行返回
            SerialPortCmdHelper.INSTANCE._CardNumber = padLeft(Integer.toHexString(data[3] & 0xFF), 2).toUpperCase()
                    + padLeft(Integer.toHexString(data[4] & 0xFF), 2).toUpperCase()
                    + padLeft(Integer.toHexString(data[5] & 0xFF), 2).toUpperCase()
                    + padLeft(Integer.toHexString(data[6] & 0xFF), 2).toUpperCase();
            Log.e("","数据校验通过");
            return true;
        }
        return false;
    }

    /**
     * Crc8的int类型校验
     *
     * @param data 需要校验的数组
     * @param len  数组长度
     * @return 校验结果
     */
    private int Acrc8(int[] data, int len) {
        int crc;
        int i;
        crc = 0;
        for (int j = 0; j < len; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; i++) {
                if ((crc & 0x01) == 1) {
                    crc = (crc >> 1) ^ 0x8C;
                } else {
                    crc >>= 1;
                }
            }
        }
        return crc;
    }

    /**
     *  CRC8校验
     * @param data
     * @return
     */
    public int FindCRC(byte[] data) {
        int CRC = 0;
        int genPoly = 0x8C;
        for (int i = 0; i < data.length; i++) {
            CRC ^= data[i];
            CRC &= 0xff;//保证CRC余码输出为1字节。
            for (int j = 0; j < 8; j++) {
                if ((CRC & 0x01) != 0) {
                    CRC = (CRC >> 1) ^ genPoly;
                    CRC &= 0xff;//保证CRC余码输出为1字节。
                } else {
                    CRC >>= 1;
                }
            }
        }
        CRC &= 0xff;//保证CRC余码输出为1字节。
        return CRC;
    }

    public String sendData = "";
    /**
     * 钥匙柜具体操作
     *
     * @param operationType，操作类型，KeyBoxOperateType类型
     * @param boxNumber，钥匙柜编号
     * @param keyNumber，钥匙编号
     * @return 成功通讯返回true，失败为false
     */
    public synchronized byte[] KeyBoxOperate(int operationType, int boxNumber, int keyNumber,String type) {
//        if (operationType == KeyBoxOperateType.writeBoxIdNextTwo || operationType == KeyBoxOperateType.writeBoxIdFirstTwo ||
////                operationType == KeyBoxOperateType.writeBoxIdFirstTwo_9_12 || operationType == KeyBoxOperateType.writeBoxIdNextTwo_13_16){
////            boxNumber = Integer.valueOf(String.valueOf(boxNumber),16);
////            keyNumber = Integer.valueOf(String.valueOf(keyNumber),16);
////            Log.e("CMD=", "keyBoxNo="+boxNumber+"---keyNo="+keyNumber);
////        }
        SerialPortCmdHelper.INSTANCE._CardNumber = "";
        //发送协议中，发送的内容为10字节
        byte[] bytesSend = new byte[10];
        //接收协议中，接收内容为18字节
        byte[] bytesGet = new byte[18];
        //拆分前的原始数据
        int[] originalData = new int[]{boxNumber, keyNumber, operationType};
//		发送：0x02 + 2箱地址 + 2钥匙地址 + 2命令号 + 2校验 + 0x04
//		接受：0x01 + 2箱地址 + 2钥匙地址 + 2命令号 + 8卡号 +2校验 + 0x03
        int i_Pos = 0, checkValue = 0;
        checkValue = Acrc8(originalData, originalData.length);
        String splitBoxNumber = "", splitKeyNumber = "", splitOperationType = "", splitCheckValue = "";
        String hexBoxNumber = padLeft(Integer.toHexString(boxNumber), 2);
        String hexKeyNumber = padLeft(Integer.toHexString(keyNumber), 2);
        String hexCheckValue = padLeft(Integer.toHexString(checkValue), 2);
        String hexoperationType = padLeft(Integer.toHexString(operationType), 2);

        splitBoxNumber = splitString(hexBoxNumber);
        splitKeyNumber = splitString(hexKeyNumber);
        splitOperationType = splitString(hexoperationType);
        splitCheckValue = splitString(hexCheckValue);

//        Log.e(type+"splitBoxNumber=",splitBoxNumber+"");
//        Log.e(type+"splitKeyNumber=",splitKeyNumber+"");
//        Log.e(type+"splitOperationType=",splitOperationType+"");
//        Log.e(type+"splitCheckValue=",splitCheckValue+"");

        //0x02
        bytesSend[i_Pos] = 0x02;
        i_Pos++;
        //2字节箱地址
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitBoxNumber.substring(0, 2), 16);
        i_Pos++;
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitBoxNumber.substring(2, 4), 16);
        i_Pos++;
        //2字节钥匙地址
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitKeyNumber.substring(0, 2), 16);
        i_Pos++;
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitKeyNumber.substring(2, 4), 16);
        i_Pos++;
        //2字节命令号
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitOperationType.substring(0, 2), 16);
        i_Pos++;
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitOperationType.substring(2, 4), 16);
        i_Pos++;
        //2字节校验
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitCheckValue.substring(0, 2), 16);
        i_Pos++;
        bytesSend[i_Pos] = (byte) Integer.parseInt(splitCheckValue.substring(2, 4), 16);
        i_Pos++;
        bytesSend[i_Pos] = 0x04;
        sendData = type + CharConversionUtil.byte2HexString(bytesSend);
        Log.e("CMD",sendData);
        return bytesSend;
    }


    private String deviceType;//设备类型
    private String deviceNo;


    public String getSendData(){
        return sendData;
    }
    public String setSendData(){
        return sendData = "";
    }


    private String lockAddress(String lockAddress) {
        StringBuffer s1 = new StringBuffer(lockAddress);
        int index;
        for (index = 2; index < s1.length(); index += 3) {
            s1.insert(index, ',');
        }
        String[] array = s1.toString().split(",");
        String[] swapOrder = swapOrder(array);
        StringBuffer s2 = new StringBuffer();
        for (String string :swapOrder ) {
            s2.append(string);
        }
        return s2.toString();
    }

    public static String[] swapOrder(String[] arr){
        int length = arr.length;
        for(int i=0;i<length/2;i++){ //只需一个循环，数组的一半就可以，第一个和最后一个交换，第二个和倒数第二个交换。。。
            String temp = arr[i];
            arr[i] = arr[length-1-i];
            arr[length-1-i] = temp;
        }
        return arr;
    }
}
