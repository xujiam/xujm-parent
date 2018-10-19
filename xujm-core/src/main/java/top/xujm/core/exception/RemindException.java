package top.xujm.core.exception;

import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.language.Language;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibSysUtil;

/**
 * @author Xujm
 */
public class RemindException extends RuntimeException {

    private int code;

    private String[] replace = null;

    public RemindException(int code,String msg) {
        super(getMsg(msg),null,false, false);
        this.code = code;
    }

    public RemindException(String msg) {
        super(getMsg(msg),null,false, false);
        this.code = BaseResultEnum.ERROR.getCode();
    }

    public RemindException(BaseResultEnum baseResultEnum) {
        super(getMsg(baseResultEnum.getMessage()),null,false, false);
        this.code = baseResultEnum.getCode();
    }

    public RemindException(int code,String msg,String[] replace) {
        super(getMsg(msg,replace),null,false, false);
        this.code = code;
        this.replace = replace;
    }

    private static String getMsg(String message){
        return getMsg(message,null);
    }

    private static String getMsg(String message,String[] replace){
        if(message == null){
            return null;
        }
        String msg = Language.getMsg(message);
        if(replace != null){
            for (String str:replace) {
                msg = String.format(msg, LibSysUtil.isInteger(str)? LibConverterUtil.toInt(str):str);
            }
        }
        return msg;
    }

    public int getCode() {
        return code;
    }

    public String[] getReplace() {
        return replace;
    }
}
