package top.xujm.modules.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {


    /**
     * 切割2018-08-24 00:00:00 - 2018-08-25 00:00:00成两个日期
     * @param s
     * @return
     */
    public static String[] subDate(String s) {
        String str1 = s.substring(0,19);
        String str2 = s.substring(22,s.length());
        String[] result = new String[2];
        result[0] = timeToString(str1);
        result[1] = timeToString(str2);
        return result;
    }

    /**
     * 切割2018-05 - 2018-07成两个日期
     * @param s
     * @return
     */
    public static String[] subDateMonth(String s) {
        String str1 = s.substring(0,7);
        String str2 = s.substring(10,s.length());
        String[] result = new String[2];
        result[0] = timeToString(str1 + "00000000");
        result[1] = timeToString(str2 + "00000000");
        return result;
    }


    /**
     * 去除例如2010-12-12 12:12:12 中的" ","-",":" 得到20121212121212
     *
     * @param s
     * @return
     */
    public static String timeToString(String s) {
        s = s.trim();
        s = StringUtils.remove(s, " ");
        s = StringUtils.remove(s,"-");
        s = StringUtils.remove(s, ":");
        return s;
    }

}
