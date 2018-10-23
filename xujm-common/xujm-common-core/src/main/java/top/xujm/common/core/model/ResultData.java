package top.xujm.common.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import top.xujm.common.core.constant.ConfigConstant;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.utils.LibConverterUtil;

import java.io.Serializable;

/**
 * @author Xujm
 */
public class ResultData<T> implements Serializable {

    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String result;

    public ResultData(){
        this.code = BaseResultEnum.SUCCESS.getCode();
    }

    public ResultData(T data){
        this.code = BaseResultEnum.SUCCESS.getCode();
        if(LibConverterUtil.toBoolean(ResourceConfig.getConfig(ConfigConstant.dataEncrypt))){
            //this.result = WkUtils.encryString(LibBeanUtil.entityToString(data));
        }else {
            this.data = data;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
