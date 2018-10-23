package top.xujm.generator.sms.captcha;

/**
 * @author Xujm
 */
public class CaptchaConfig {

    private Integer expireTime;

    private Integer hoursNum;

    private Integer dayNum;

    private Integer length;

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getHoursNum() {
        return hoursNum;
    }

    public void setHoursNum(Integer hoursNum) {
        this.hoursNum = hoursNum;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
