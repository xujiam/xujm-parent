package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author Xujm
 */
@ApiModel("用户信息返回实体")
public class ResultUserInfo extends UserInfo {

    @ApiModelProperty(value = "关注状态(0未关注1关注2互关注)")
    private byte followState;

    @ApiModelProperty("关注数")
    private int followNum;

    @ApiModelProperty("粉丝数")
    private int fansNum;

    @ApiModelProperty(value = "总消费")
    private double totalConsume;

    @ApiModelProperty(value = "用户标签")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> label;

    public byte getFollowState() {
        return followState;
    }

    public void setFollowState(byte followState) {
        this.followState = followState;
    }

    @Override
    @Transient
    public int getFollowNum() {
        return followNum;
    }

    @Override
    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }
    @Override
    @Transient
    public int getFansNum() {
        return fansNum;
    }

    @Override
    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    @Transient
    public double getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(double totalConsume) {
        this.totalConsume = totalConsume;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

}
