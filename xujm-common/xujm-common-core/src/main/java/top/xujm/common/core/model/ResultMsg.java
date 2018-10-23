package top.xujm.common.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import top.xujm.core.base.BaseResultEnum;

import java.io.Serializable;

/**
 * @author Xujm
 */
public class ResultMsg implements Serializable {

    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public ResultMsg(int code,String message){
        this.code = code;
        this.message = message;
    }

    public ResultMsg(){
        this.code = BaseResultEnum.SUCCESS.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

}
