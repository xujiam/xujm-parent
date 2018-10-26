package top.xujm.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.msg.model.ImBaseInfo;

/**
 * @author Xujm
 */
@ApiModel("@用户消息实体")
public class ImAltUser extends ImBaseInfo {

    @ApiModelProperty("@消息来源代号(如观点@用户为dynamic)")
    private String sourceCode;

    @ApiModelProperty("@消息来源ID(如观点@用户为观点ID)")
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
}
