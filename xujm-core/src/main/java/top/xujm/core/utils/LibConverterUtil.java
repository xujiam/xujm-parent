package top.xujm.core.utils;

import org.springframework.util.StringUtils;

/**
 * 数据类型转换工具类
 * @author Xujm
 */
public class LibConverterUtil {

    /**
     * 转换为double类型
     * @param value 转换的值
     */
    public static double toDouble(Object value) {
        return toDouble(value, 0D);
    }

    public static double toDouble(Object value, double defaultValue) {
        double result = defaultValue;
        try {
            if (!StringUtils.isEmpty(value)){
                result = Double.parseDouble(value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转换为Byte类型
     * @param value 转换的值
     */
    public static byte toByte(Object value) {
        return toByte(value, 0);
    }

    public static byte toByte(Object value, int defaultValue) {
        byte result = (byte) defaultValue;
        try {
            if (!StringUtils.isEmpty(value)){
                result = Byte.parseByte(value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转换为int类型
     * @param value 转换的值
     */
    public static int toInt(Object value) {
        return toInt(value, 0);
    }

    public static int toInt(Object value, int defaultValue) {
        int result = defaultValue;
        try {
            if (!StringUtils.isEmpty(value)){
                result = Integer.parseInt(value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转换为String类型
     * @param value 转换的值
     */
    public static String toString(Object value) {
        String result = "";
        try {
            if (value != null){
                result = value.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转换为boolean类型
     * @param value 转换的值
     */
    public static boolean toBoolean(Object value) {
        return toBoolean(value, false);
    }

    public static boolean toBoolean(Object value, boolean defaultValue) {
        boolean result = defaultValue;
        try {
            if (value != null) {
                String v = value.toString();
                result = "1".equals(v) || Boolean.parseBoolean(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 转换为boolean类型
     * @param value 转换的值
     */
    public static long toLong(Object value) {
        return toLong(value, 0);
    }

    public static long toLong(Object value, long defaultValue) {
        long result = defaultValue;
        try {
            if (!StringUtils.isEmpty(value)){
                result = Long.parseLong(value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(toBoolean("1"));
    }


}
