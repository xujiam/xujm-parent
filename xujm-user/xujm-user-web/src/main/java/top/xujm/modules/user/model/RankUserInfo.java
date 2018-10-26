package top.xujm.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Xujm
 */
@ApiModel("排行实体")
public class RankUserInfo extends UserBaseInfo{
    @ApiModelProperty("数量(消费数，关注数)")
    private int qty;
    @ApiModelProperty("排行数量")
    private int followState;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getFollowState() {
        return followState;
    }

    public void setFollowState(int followState) {
        this.followState = followState;
    }
}
