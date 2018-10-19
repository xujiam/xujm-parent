package top.xujm.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xujm.core.base.BaseConstants;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.language.Language;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Xujm
 */
public class LibSysUtil {

    private static Logger logger = LoggerFactory.getLogger(LibSysUtil.class);

    private static Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    private static Pattern MOBILE_PATTERN = Pattern.compile("^1\\d{10}$");

    private static Pattern ABROAD_MOBILE_PATTERN = Pattern.compile("[1-9]\\d{4,12}$");

    private static Pattern DOUBLE_PATTERN = Pattern.compile("^[-+]?[.\\d]*$");

    /**
     * 判断Map是否为空
     * @param map
     * @return
     */
    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
        return (map == null || map.size() < 1);
    }

    /**
     * 判断数组是否为空
     * @param obj
     * @return
     */
    public static boolean isEmptyArray(Object[] obj) {
        return (obj == null || obj.length < 1);
    }

    public static boolean isInteger(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return INTEGER_PATTERN.matcher(str).matches();
    }

    public static boolean isDouble(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return DOUBLE_PATTERN.matcher(str).matches();
    }

    public static boolean isJsonStr(String str){
        try {
            JSONObject.parseObject(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isJsonArr(String str){
        try {
            JSON.parseArray(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否手机号
     */
    public static boolean isMobile(String mobile){
        return isMobile(mobile, BaseConstants.CHINA_AREA_CODE);
    }

    public static boolean isMobile(String mobile,String areaCode){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        if(StringUtils.equals(areaCode,BaseConstants.CHINA_AREA_CODE)){
            return MOBILE_PATTERN.matcher(mobile).matches();
        }else{
            return ABROAD_MOBILE_PATTERN.matcher(mobile).matches();
        }
    }

    /**
     * 是否经度
     */
    public static boolean isLng(double lng){
        return lng >= -180 && lng <= 180;
    }

    /**
     * 是否纬度
     */
    public static boolean isLat(double lat){
        return lat >= -90 && lat <= 90;
    }

    /**
     * 获得当前服务器IP
     */
    public static String getCurServiceIp(){
        InetAddress host;
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        //获得本机IP
        return host.getHostAddress();
    }

    public static Map<String,String> listToMap(List<Object> list,String... fields){
        if (list.get(0) == null) {
            return null;
        }
        Map<String,String> map = new HashMap<>(0);
        int len = fields.length;
        for (int i = 0; i < len; i++) {
            map.put(fields[i], LibConverterUtil.toString(list.get(i)));
        }
        return map;
    }

    /**
     * 是否为空，增加了项目代表空字符串判断
     * @param cs cs
     * @return boolean
     */
    public static boolean isEmpty(CharSequence cs){
        return StringUtils.isEmpty(cs) || StringUtils.equals(cs,BaseConstants.DEFAULT_EMPTY);
    }

    public static JSONObject getResultJSON() {
        return getResultJSON(BaseResultEnum.SUCCESS.getCode(),null);
    }

    public static JSONObject getResultJSON(JSONArray jsonArray){
        return getResultJSON(jsonArray,null);
    }

    public static JSONObject getResultJSON(JSONObject data){
        return getResultJSON(null,data);
    }

    public static JSONObject getResultJSON(String data){
        return getResultJSON(null,JSONObject.parseObject(data));
    }

    public static JSONObject getResultJSON(JSONArray jsonArray,JSONObject data) {
        JSONObject result = new JSONObject();
        result.put(BaseConstants.RESULT_CODE, BaseResultEnum.SUCCESS.getCode());
        if(jsonArray != null){
            result.put(BaseConstants.RESULT_LIST,jsonArray);
        }
        if(data != null){
            result.put(BaseConstants.RESULT_DATA,data);
        }
        return result;
    }

    public static JSONObject getResultJSON(int code,String message){
        JSONObject result = new JSONObject();
        result.put(BaseConstants.RESULT_CODE, code);
        if(message != null){
            result.put(BaseConstants.RESULT_MESSAGE, Language.getMsg(message));
        }
        return result;
    }

    public static void out(HttpServletResponse response, JSONObject result) {
        out(response, result.toString());
    }

    public static void out(HttpServletResponse response, String result) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(result);
            printWriter.flush();
            printWriter.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    /**
     * 是否url(只验证是否包含http,https)
     */
    public static boolean isUrl(String str){
        boolean flag = false;
        if(str.indexOf("http://")==0 || str.indexOf("https://")==0){
            flag = true;
        }
        return flag;
    }

    public static String urlDecode(String data){
        try{
            data = URLDecoder.decode(data,"utf-8");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return data;
    }

    public static String urlEncode(String data){
        try{
            data = URLEncoder.encode(data,"utf-8");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取请求客户端真实IP
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress iNet = null;
                    try {
                        iNet = InetAddress.getLocalHost();
                        ipAddress = iNet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }

    public static void main(String[] args){
        System.out.println((double) 45/47);
//        NumberFormat numberFormat = NumberFormat.getInstance();
//         // 设置精确到小数点后2位
//         numberFormat.setMaximumFractionDigits(2);
//         String result = numberFormat.format(45.00 / 47.00);
//         System.out.println("num1和num2的百分比为:" + result + "%");
    }



}
