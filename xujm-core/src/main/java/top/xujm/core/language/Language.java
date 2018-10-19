package top.xujm.core.language;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import top.xujm.core.utils.SpringContextUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author Xujm
 */
public class Language {

    public static Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    private static MessageSource messageSource;

    static {
        messageSource = (MessageSource) SpringContextUtil.getBean("messageSource");
    }
    /**
     * 获得相应语言文字
     */
    public static String getMsg(String langKey){
        try{
            return messageSource.getMessage(langKey, null,getLangCode());
        }catch (Exception e){
            return langKey;
        }
    }

    /**
     * 获得相应语言文字
     */
    public static String getMsg(String langKey,String langCode){
        try{
            return messageSource.getMessage(langKey, null,getLocale(langCode));
        }catch (Exception e){
            return langKey;
        }
    }

    /**
     * 获得当前语言
     */
    public static Locale getLangCode(){
        return LocaleContextHolder.getLocale();
    }

    public static Locale getLocale(String langCode){
        String[] language = langCode.split("_");
        if(language.length == 2){
            return new Locale(language[0], language[1]);
        }
        return DEFAULT_LOCALE;
    }

    public static void setLocale(HttpServletRequest request){
        LocaleContextHolder.setLocale(getLocale(request));
    }

    public static Locale getLocale(HttpServletRequest request){
        String langCode = request.getParameter("lang_code");
        if(!StringUtils.isEmpty(langCode)){
            return getLocale(langCode);
        }
        langCode = request.getHeader("Accept-Language");
        if(langCode == null){
            return DEFAULT_LOCALE;
        }
        return getLocale(langCode);
    }



}
