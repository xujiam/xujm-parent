package top.xujm.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * @author Xujm
 */
public class LibRandomUtil {

    /**
     * 定义所有的字符组成的串
     */
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     *  定义所有的字母组成的串（不包括数字）
     */
    private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 定义所有的数字字符组成的串
     */
    private static final String NUMBER_CHAR = "0123456789";

    /**
     * 产生长度为length的随机字符串（包括字母和数字）
     * @param length 长度
     * @return String
     */
    public static String randomStr(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }
    /**
     * 产生长度为length的随机数字字符串
     * @param length 长度
     * @return String
     */
    public static String randomNumber(int length){
        return RandomStringUtils.randomNumeric(length);
    }
    /**
     * 产生长度为length的随机字母字符串（包括字母，不包括数字）
     */
    public static String randomLetter(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }
    /**
     * 产生长度为length的随机小写字符串（包括字母，不包括数字）
     */
    public static String randomLowerLetter(int length) {
        return randomLetter(length).toLowerCase();
    }
    /**
     * 产生长度为length的随机大写字符串（包括字母，不包括数字）
     */
    public static String randomUpperLetter(int length) {
        return randomLetter(length).toUpperCase();
    }
    /**
     * 产生长度为length的'0'串
     */
    public static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
    /**
     * 将输入的long整数值补全0成为fixLength长度的字符串
     */
    public static String toFixLengthString(long num, int fixLength) {
        StringBuilder sb = new StringBuilder();
        String strNum = String.valueOf(num);
        if (fixLength - strNum.length() >= 0) {
            sb.append(generateZeroString(fixLength - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixLength + "的字符串发生异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 将输入的int整数值补全0成为fixLength长度的字符串
     */
    public static String toFixLengthString(int num, int fixLength) {
        return toFixLengthString(LibConverterUtil.toLong(num),fixLength);
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static void main(String[] args) {

    }


}
