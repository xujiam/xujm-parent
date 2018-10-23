package top.xujm.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Xujm
 * 2018/8/31
 */
@ApiModel("关注基础实体")
public class UserFollowBaseInfo extends UserBaseInfo {

    @ApiModelProperty("关注数")
    private Integer followNum;
    @ApiModelProperty("粉丝数")
    private Integer fansNum;
    @ApiModelProperty(value = "关注状态(0未关注1关注2互关注)")
    private Byte followState;

    protected UserFollowBaseInfo(){
    }

    public UserFollowBaseInfo(int userId, String account, String nickname, String avatar, byte sex, byte level
            , String signature,short role,Integer followNum,Integer fansNum){
        super(userId, account, nickname, avatar,sex, level,signature,role);
        this.fansNum = fansNum;
        this.followNum = followNum;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Byte getFollowState() {
        return followState;
    }

    public void setFollowState(Byte followState) {
        this.followState = followState;
    }
}
