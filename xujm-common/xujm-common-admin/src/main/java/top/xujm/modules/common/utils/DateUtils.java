
package top.xujm.modules.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 日期工具类
 *
 * @author liuwj
 */
public class DateUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");


    // 获取当前日期
    public static String now() {
        String result = "";
        try {
            Calendar ca = Calendar.getInstance();
            result = format.format(ca.getTime());
        } catch (Exception e) {

        }
        return result;
    }

    // 获取近一个星期的日期
    public static String getLastWeek() {
        String result = "";
        try {
            Calendar ca = Calendar.getInstance();
            ca.setTime(new Date());
            ca.add(Calendar.DATE, - 7);
            result = format.format(ca.getTime());
        } catch (Exception e) {
        }
        return result;
    }

    // 获取当前日期yyyyMMddHHmmss格式
    public static long getLongNowtime() {

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        long result = Long.parseLong(time);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(now());
    }
}
