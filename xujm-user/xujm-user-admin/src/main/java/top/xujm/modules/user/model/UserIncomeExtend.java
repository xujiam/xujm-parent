package top.xujm.modules.user.model;

public class UserIncomeExtend extends UserIncome {

    private String incomeMark;

    public String getIncomeMark() {
        return incomeMark;
    }

    public void setIncomeMark(String incomeMark) {
        this.incomeMark = incomeMark;
    }

    public UserIncomeExtend(String incomeMark) {
        this.incomeMark = incomeMark;
    }

    public UserIncomeExtend(int id, int userId, String account, String nickname, String avatar, double incomeNum, String incomeCode, int moduleId, int extendId, long addTime, String incomeMark) {
        super(id, userId, account, nickname, avatar, incomeNum, incomeCode, moduleId, extendId, addTime);
        this.incomeMark = incomeMark;
    }
}
