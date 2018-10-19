package top.xujm.core.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Xujm
 */
public class LibDateUtil {

    private static Logger logger = LoggerFactory.getLogger(LibDateUtil.class);

    public static String dayDate = "yyyyMMdd";
    public static String dayDateZero = "yyyyMMdd000000";
    public static String monthDate = "yyyyMM";
    public static String monthDateZero = "yyyyMM00000000";
    public static String secondDate = "yyyyMMddHHmmss";



    /**
     * 获得当前时间
     */
    public static Long getNowTime(){
        return formatDate(getNowDate());
    }

    public static Long getNowDay(){
        return LibConverterUtil.toLong(getDateFormat(dayDate).format(getNowDate()));
    }

    public static Long formatDate(Date date){
        return LibConverterUtil.toLong(getDateFormat().format(date));
    }
    /**
     * 获得当天时间到日期，其他时间用0补齐
     */
    public static Long getCurDayTime(){
        return getCurDayTime(dayDateZero);
    }

    public static long getCurDayTime(String pattern){
        return LibConverterUtil.toLong(getDateFormat(pattern).format(getNowDate()));
    }
    /**
     * 获得当前年月份
     */
    public static Long getNowYearMonth(){
       return getNowYearMonth(monthDate);
    }

    /**
     * 获得当前年月份
     */
    public static long getNowYearMonth(String pattern){
        return LibConverterUtil.toLong(getDateFormat(pattern).format(getNowDate()));
    }

    public static long getNowMillis(){
        return System.currentTimeMillis();
    }

    /**
     * 格式化时间转时间戳
     */
    public static long dateToStamp(long dateTime){
        FastDateFormat dateFormat = getDateFormat();
        try {
            Date startDate = dateFormat.parse(LibConverterUtil.toString(dateTime));
            return startDate.getTime();
        } catch (ParseException e) {
            logger.error("dateToStamp:{},{}",dateTime,e);
        }
        return 0;
    }

    public static long getDateTimeTickToSecond(long start){
        return getDateTimeTick(start)/1000;
    }

    public static long getDateTimeTickToSecond(long start, long end){
        return getDateTimeTick(start,end)/1000;
    }

    /**
     * 当前时间到该时间的时间戳差
     */
    public static long getDateTimeTick(long start){
        return getNowMillis() - dateToStamp(start);
    }

    /**
     * 获取两个时间之间的时间戳
     * @param start  起始时间，格式yyyyMMddHHmmss
     * @param end   结束时间，格式yyyyMMddHHmmss
     * @return 时间戳
     */
    public static long getDateTimeTick(long start, long end) {
        FastDateFormat dateFormat = getDateFormat();
        try {
            Date startDate = dateFormat.parse(LibConverterUtil.toString(start));
            Date endDate = dateFormat.parse(LibConverterUtil.toString(end));
            return getDateTimeTick(startDate, endDate);
        } catch (ParseException e) {
            logger.error("getDateTimeTick:{},{},{}",start,end,e);
        }
        return 0;
    }

    public static long getDateTimeTick(Date from, Date end) {
        return end.getTime() - from.getTime();
    }

    public static FastDateFormat getDateFormat(){
        return getDateFormat(secondDate);
    }

    public static String getPatternFormat(String pattern){
        return getDateFormat(pattern).format(getNowDate());
    }

    public static String getPatternFormat(String pattern,Long timeStamp){
        if(timeStamp == null){
            return null;
        }
        return getDateFormat(pattern).format(new Date(timeStamp));
    }

    public static String getPatternFormat(String pattern,Integer timeStamp){
        if(timeStamp == null){
            return null;
        }
        return getPatternFormat(pattern,LibConverterUtil.toLong(timeStamp) * 1000);
    }

    public static FastDateFormat getDateFormat(String pattern){
        return FastDateFormat.getInstance(pattern);
    }

    public static Date getNowDate(){
        return new Date();
    }

    public static Long curTimeAddHours(int amount){
        return formatDate(DateUtils.addHours(getNowDate(),amount));
    }

    public static Long curTimeAddDay(int amount){
        return DateUtils.addDays(getNowDate(),amount).getTime()/1000;
    }

    public static Long curTimeStampAddMinute(int amount){
        return DateUtils.addMinutes(getNowDate(),amount).getTime()/1000;
    }

    public static long curTimeAddMonth(int amount,String pattern){
        return LibConverterUtil.toLong(getDateFormat(pattern).format(DateUtils.addMonths(getNowDate(),amount)));
    }

    public static Long timeAddMonth(String time,int amount,String pattern){
        Date date;
        try {
            date = getDateFormat(pattern).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return LibConverterUtil.toLong(getDateFormat(pattern).format(DateUtils.addMonths(date,amount)));
    }

    public static int curTimeAddMonth(int amount){
        return LibConverterUtil.toInt(curTimeAddMonth(amount,monthDate));
    }

    public static Long curTimeAddMinute(int amount){
        return formatDate(DateUtils.addMinutes(getNowDate(),amount));
    }

    public static boolean isValidDate(String str){
        return isValidDate(str,secondDate);
    }

    public static boolean isValidDate(String str,String pattern) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static String formatString(long tick) {
        String result = "";
        try {
            long day = tick / 86400000L;
            long hour = (tick - day * 1000L * 60L * 60L * 24L) / 3600000L;
            long min = (tick - day * 1000L * 60L * 60L * 24L - hour * 1000L * 60L * 60L) / 60000L;
            long second = (tick - day * 1000L * 60L * 60L * 24L - hour * 1000L * 60L * 60L - min * 1000L * 60L) / 1000L;
            String ssecond = second < 10L ? String.format("0%s", second) : LibConverterUtil.toString(second);
            String smin = min < 10L ? String.format("0%s", min) : LibConverterUtil.toString(min);
            hour += day * 24L;
            String shour = hour < 10L ? String.format("0%s", hour) : LibConverterUtil.toString(hour);
            result = String.format("%s:%s:%s", shour, smin, ssecond);
        } catch (Exception var15) {
            var15.printStackTrace();
        }

        return result;
    }

    public static int getYear(){
        return getCalendar().get(Calendar.YEAR);
    }

    public static int getMonth(){
        return getCalendar().get(Calendar.MONTH);
    }

    public static int getDate(){
        return getCalendar().get(Calendar.DATE);
    }

    public static int getHour(){
        return getCalendar().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(){
        return getCalendar().get(Calendar.MINUTE);
    }

    public static int getSecond(){
        return getCalendar().get(Calendar.SECOND);
    }

    private static Calendar getCalendar(){
        return Calendar.getInstance();
    }

    public static void main(String[] args){
        System.out.println(curTimeAddMonth(-1));
    }

}
