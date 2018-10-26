package top.xujm.msg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Xujm
 */
@ApiModel("消息推送基础信息")
public class ImBasePushInfo extends ImBaseInfo{

    @ApiModelProperty("是否屏蔽")
    private boolean isShield;
    @ApiModelProperty("消息来源代号(如发布观点[dynamic])")
    private String sourceCode;
    @ApiModelProperty("消息对象ID(如是发布观点{dynamicId})")
    private int objectId;

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public boolean isShield() {
        return isShield;
    }

    public void setShield(boolean shield) {
        isShield = shield;
    }
}
