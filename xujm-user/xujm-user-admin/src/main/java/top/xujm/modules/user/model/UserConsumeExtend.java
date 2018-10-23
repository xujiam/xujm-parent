package top.xujm.modules.user.model;

public class UserConsumeExtend extends UserConsume{

    private String toaccount;

    private String tonickname;

    private String consumeMark;

    public String getToaccount() {
        return toaccount;
    }

    public void setToaccount(String toaccount) {
        this.toaccount = toaccount;
    }

    public String getTonickname() {
        return tonickname;
    }

    public void setTonickname(String tonickname) {
        this.tonickname = tonickname;
    }

    public String getConsumeMark() {
        return consumeMark;
    }

    public void setConsumeMark(String consumeMark) {
        this.consumeMark = consumeMark;
    }

    public UserConsumeExtend(String toaccount, String tonickname, String consumeMark) {
        this.toaccount = toaccount;
        this.tonickname = tonickname;
        this.consumeMark = consumeMark;
    }

    public UserConsumeExtend(int id, int userId, String account, String nickname, String avatar, int toId, double costNum, double incomeNum, String consumeCode, int moduleId, int extendId, long addTime, String toaccount, String tonickname, String consumeMark) {
        super(id, userId, account, nickname, avatar, toId, costNum, incomeNum, consumeCode, moduleId, extendId, addTime);
        this.toaccount = toaccount;
        this.tonickname = tonickname;
        this.consumeMark = consumeMark;
    }
}
