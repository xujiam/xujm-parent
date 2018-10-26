package top.xujm.core.utils;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Xujm
 * 2018/10/23
 */
public class LibPropertiesUtil extends Properties{

    private static Logger logger = LoggerFactory.getLogger(LibPropertiesUtil.class);

    private static Map<String,String> configMap = null;

    private static Properties props;

    public static String getConfig(String key){
        getAllResource();
        return configMap.get(key);
    }


    private static void getAllResource(){
        if(configMap == null){
            configMap = new HashMap<>(0);
            try {
                LibPropertiesUtil libProperties = new LibPropertiesUtil();
                libProperties.load(new InputStreamReader(LibPropertiesUtil.class.getResourceAsStream("/projectConfig.properties"), StandardCharsets.UTF_8));
                Enumeration enum1 = libProperties.propertyNames();
                while(enum1.hasMoreElements()) {
                    String strKey = (String)enum1.nextElement();
                    String strValue = libProperties.getProperty(strKey);
                    configMap.put(strKey, strValue);
                }
            } catch (IOException var6) {
                var6.printStackTrace();
            } catch (NullPointerException e){
                e.getMessage();
            }
        }
    }

}
