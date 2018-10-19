package top.xujm.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Xujm
 */
public class LibBeanUtil {

    private static Logger logger = LoggerFactory.getLogger(LibBeanUtil.class);
    /**
     * 获取实例属性名
     * */
    public static String[] getBeanAttributeName(Class c){
        Field[] fields = c.getDeclaredFields();
        String[] strArr = new String[fields.length];
        int i = 0;
        for (Field field : fields) {
            strArr[i] = field.getName();
            i++;
        }
        return strArr;
    }

    public static Object mapToObject(Map<?,?> map, Class<?> beanClass){
        return entityCopy(map, beanClass);
    }


    public static <T>T entityCopy(Object object, Class<T> beanClass){
        if (object == null){
            return null;
        }
        String json = JSON.toJSONString(object);
        return JSON.parseObject(json, beanClass);
    }

    public static String entityToString(Object object){
        if (object == null){
            return null;
        }
        return JSON.toJSONString(object);
    }

    public static JSONObject entityToJsonObject(Object object){
        if (object == null){
            return null;
        }
        String json = JSON.toJSONString(object);
        return JSON.parseObject(json);
    }

    public static Map<String, String> objectToMap(Object object){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Map.Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, String> map=new HashMap<>(0);
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), LibConverterUtil.toString(entry.getValue()));
        }
        return map;
    }

    public static void main(String[] args){

    }

}
