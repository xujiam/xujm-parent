package top.xujm.core.base;

import top.xujm.core.language.Language;

/**
 * @author Xujm
 */
public class BaseService {

    public String getClientLangCode(){
        return Language.getLangCode().toString();
    }

}
