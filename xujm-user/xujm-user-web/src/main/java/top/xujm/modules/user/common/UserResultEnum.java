package top.xujm.modules.user.common;

/**
 * @author Xujm
 */
public enum UserResultEnum {

    /**
     * 反馈内容为空
     */
    FEEDBACK_CONTENT_EMPTY(2310,"user.feedback.content.empty"),
    /**
     * 被对方拉黑
     */
    BLACK(2303,"user.black.to.exist"),
    /**
     * 不能拉黑自己
     */
    BLACK_SELF(2302,"user.black.self"),

    /**
     * 未拉黑
     */
    BLACK_NOT_EXIST(2301,"user.black.not.exist"),
    /**
     * 已拉黑
     */
    BLACK_EXIST(2300,"user.black.exist"),


    /**
     * 用户不存在
     */
    USER_NOT_EXIST(2000,"user.user.not.exist"),
    /**
     * 出生日期有误
     */
    BIRTHDAY_ERROR(2001,"user.birthday.error"),

    /**
     * 已关注
     */
    FOLLOW_EXIST(2100,"user.follow.exist"),

    /**
     * 关注自己
     */
    FOLLOW_SELF(2101,"user.follow.self"),

    /**
     * 消费方式不存在
     */
    CONSUME_NOT_EXIST(2200,"user.pocket.consume.not.exist"),

    /**
     * 消费方式错误
     */
    CONSUME_WAY_ERROR(2201,"user.pocket.consume.way.error"),

    /**
     * 余额不足
     */
    NOT_MONEY(2202),

    /**
     * 冻结资金不存在
     */
    FROZEN_ERROR(2203,"user.pocket.frozen.error"),

    /**
     * 收入类型不存在
     */
    INCOME_NOT_EXIST(2204,"user.pocket.income.not.exist"),

    /**
     * 收入方式错误
     */
    INCOME_WAY_ERROR(2205,"user.pocket.income.way.error"),

    /**
     * ACCOUNT已存在
     */
    ACCOUNT_EXIST(2206,"user.account.exist");

    private int code;

    private String message;

    UserResultEnum(int code){
        this.code = code;
    }

    UserResultEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

}
