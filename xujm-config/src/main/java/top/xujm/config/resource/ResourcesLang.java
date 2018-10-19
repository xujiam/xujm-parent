package top.xujm.config.resource;

import top.xujm.core.language.Language;
import top.xujm.core.utils.SpringContextUtil;
import top.xujm.modules.config.model.PlatformLang;
import top.xujm.modules.config.repository.PlatformLangRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public class ResourcesLang {

    private final static Map<String,String> PLATFORM_LANG = new HashMap<>();

    static {
        init();
    }

    public static void refresh(){
        init();
    }

    private static void init(){
        List<PlatformLang> list = getPlatformLang().findAll();
        for (PlatformLang info:list){
            PLATFORM_LANG.put(buildKey(info.getLangKey(),info.getLangCode()),info.getLangContext());
        }
    }

    public static void setLang(String langKey,String langContext,String langCode){
        PLATFORM_LANG.put(buildKey(langKey,langCode),langContext);
    }

    public static String getLang(String langKey){
        return getLang(langKey, Language.getLangCode().toString());
    }

    public static String getLang(String langKey,String langCode){
        String val = PLATFORM_LANG.get(buildKey(langKey,langCode));
        if(val == null){
            return langKey;
        }
        return val;
    }

    private static String buildKey(String langKey,String langCode){
        return String.format("%s.%s",langKey,langCode);
    }

    private static PlatformLangRepository getPlatformLang(){
        PlatformLangRepository platformLangRepository = (PlatformLangRepository) SpringContextUtil.getBean("platformLangRepository");
        if(platformLangRepository == null){
            throw new RuntimeException("platformLangRepository not found");
        }
        return platformLangRepository;
    }

}
