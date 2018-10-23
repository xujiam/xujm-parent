package top.xujm.modules.user.model;


public class UserFollowExtend extends UserFollow{

    private String toaccount;

    private String tonickname;

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

    public UserFollowExtend(String toaccount, String tonickname) {
        this.toaccount = toaccount;
        this.tonickname = tonickname;
    }

    public UserFollowExtend(int id, int userId, int toId, String account, String nickname, String avatar, long addTime, short role, byte followState, String toaccount, String tonickname) {
        super(id, userId, toId, account, nickname, avatar, addTime, role, followState);
        this.toaccount = toaccount;
        this.tonickname = tonickname;
    }

}
