package top.xujm.core.utils;

/**
 * @author Xujm
 * 2018/9/5
 */
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LibUrlUtil {

    /**
     * 向url链接追加参数
     * @param url 链接
     * @param params Map<String, String>
     * @return  String
     */
    public static String appendParams(String url, Map<String, String> params){
        if(StringUtils.isBlank(url)){
            return "";
        }else if(LibSysUtil.isEmptyMap(params)){
            return url.trim();
        }else{
            StringBuilder sb = new StringBuilder();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);

            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            //url说明有问号
            if(index > -1){
                //url最后一个符号为？，如：http://wwww.baidu.com?
                if((length - 1) == index){
                    url += sb.toString();
                }else{
                    //情况为：http://wwww.baidu.com?aa=11
                    url += "&" + sb.toString();
                }
            }else{
                //url后面没有问号，如：http://wwww.baidu.com
                url += "?" + sb.toString();
            }
            return url;
        }
    }

    /**
     * 向url链接追加参数(单个)
     * @param url 链接
     * @param name String
     * @param value String
     * @return
     */
    public static String appendParam(String url, String name, String value){
        if(StringUtils.isBlank(url)){
            return "";
        }else if(StringUtils.isBlank(name)){
            return url.trim();
        }else{
            Map<String, String> params = new HashMap<>();
            params.put(name, value);
            return appendParams(url, params);
        }
    }

    /**
     * 移除url链接的多个参数
     * @param url String
     * @param paramNames String[]
     * @return String
     */
    public static String removeParams(String url, String... paramNames){
        if(StringUtils.isBlank(url)){
            return "";
        }else if(LibSysUtil.isEmptyArray(paramNames)){
            return url.trim();
        }else{
            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            //url说明有问号
            if(index > -1){
                if((length - 1) == index){
                    //url最后一个符号为？，如：http://wwww.baidu.com?
                    return url;
                }else{
                    //情况为：http://wwww.baidu.com?aa=11或http://wwww.baidu.com?aa=或http://wwww.baidu.com?aa
                    String baseUrl = url.substring(0, index);
                    String paramsString = url.substring(index + 1);
                    String[] params = paramsString.split("&");
                    if(!LibSysUtil.isEmptyArray(params)){
                        Map<String, String> paramsMap = new HashMap<>();
                        for (String param : params) {
                            if(!StringUtils.isBlank(param)){
                                String[] oneParam = param.split("=");
                                String paramName = oneParam[0];
                                int count = 0;
                                for (String paramName1 : paramNames) {
                                    if (paramName1.equals(paramName)) {
                                        break;
                                    }
                                    count++;
                                }
                                if(count == paramNames.length){
                                    paramsMap.put(paramName, (oneParam.length > 1)?oneParam[1]:"");
                                }
                            }
                        }
                        if(!LibSysUtil.isEmptyMap(paramsMap)){
                            StringBuilder paramBuffer = new StringBuilder(baseUrl);
                            paramBuffer.append("?");
                            Set<String> set = paramsMap.keySet();
                            for (String paramName : set) {
                                paramBuffer.append(paramName).append("=").append(paramsMap.get(paramName)).append("&");
                            }
                            paramBuffer.deleteCharAt(paramBuffer.length() - 1);
                            return paramBuffer.toString();
                        }
                        return baseUrl;
                    }
                }
            }
            return url;
        }
    }

    /**
     * @param paramUrl 带分隔的url参数
     * @return
     */
    public static Map<String,String> paramToMap(String paramUrl){
        Map<String,String> map = new HashMap<>();
        int index = paramUrl.indexOf("?");
        //如果有问号截取问号后参数
        if(index > -1){
            paramUrl = paramUrl.substring(index + 1);
        }
        String[] param =  paramUrl.split("&");
        for(String value:param){
            String[] pair = value.split("=");
            if(pair.length==2){
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String url = "https://www.facebook.com/v2.10/dialog/oauth?client_id=474938339602522&response_type=code&redirect_uri=http%3A%2F%2F192.168.50.29%2Fuser%2Flogin%2Ffacebook&state=650d4d82-c4a6-45ea-a869-3b417866be85";
        System.out.println(paramToMap(LibSysUtil.urlDecode(url)).toString());
    }
}
