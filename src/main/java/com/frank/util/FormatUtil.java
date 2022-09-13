package com.frank.util;

import java.util.Map;
import java.util.TreeMap;

public final class FormatUtil {
    private static final int KEY_OBJECT_LENGTH = 2;
    /** ==============IS Base=================== */
    /** 判断是否为空 ,为空返回true */
    public static boolean isEmpty(Object arg) {
        return toStringTrim(arg).length() == 0;
    }

    /**
     * Object 转换成 String 为null 返回空字符 <br> 使用:toString(值,默认值[选填])
     */
    public static String toString(Object... args) {
        String def = "";
        if (args != null) {
            if (args.length > 1) {
                def = toString(args[args.length - 1]);
            }
            Object obj = args[0];
            if (obj == null) {
                return def;
            }
            return obj.toString();
        } else {
            return def;
        }
    }

    /**
     * Object 转换成 String[去除所以空格]; 为null 返回空字符 <br> 使用:toStringTrim(值,默认值[选填])
     */
    public static String toStringTrim(Object... args) {
        String str = toString(args);
        return str.replaceAll("\\s*", "");
    }

    /**
     * 将byte[] 转换成字符串
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSb = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSb.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSb.toString();
    }

    /**
     * 将16进制字符串转为转换成字符串
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    /**
     * TreeMa集合2String
     *
     * @param data TreeMa
     * @return String
     */
    public static String coverMap2String(Map<String, String> data) {
        StringBuilder sf = new StringBuilder();
        for (String key : data.keySet()) {
            if (!isBlank(data.get(key))) {
                sf.append(key).append("=").append(data.get(key).trim()).append("&");
            }
        }
        return sf.substring(0, sf.length() - 1);
    }

    /**
     * 空值判断
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回参数处理
     *
     * @throws Exception
     */
    public static Map<String, String> getParam(String rstr) throws Exception {
        Map<String, String> dateArry = new TreeMap<String, String>();
        String[] listObj = rstr.split("&");
        for (String temp : listObj) {
            if (temp.matches("(.+?)=(.+?)")) {
                String[] tempListObj = temp.split("=");
                dateArry.put(tempListObj[0], tempListObj[1]);
            } else if (temp.matches("(.+?)=")) {
                String[] tempListObj = temp.split("=");
                dateArry.put(tempListObj[0], "");
            } else {
                throw new Exception("参数无法分解！");
            }
        }
        return dateArry;
    }

    /**
     * 获取密钥
     *
     * @throws Exception
     */
    public static String getAesKey(String keyStr) throws Exception {
        String[] listKeyObj = keyStr.split("\\|");
        if (listKeyObj.length == KEY_OBJECT_LENGTH) {
            if (!listKeyObj[1].trim().isEmpty()) {
                return listKeyObj[1];
            } else {
                throw new Exception("Key is Null!");
            }
        } else {
            throw new Exception("Data format is incorrect!");
        }
    }
}