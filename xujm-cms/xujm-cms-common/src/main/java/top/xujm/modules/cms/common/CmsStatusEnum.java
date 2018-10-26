package top.xujm.modules.cms.common;

/**
 * @author Xujm
 * 2018/10/25
 */
public enum CmsStatusEnum {
    /**
     * 草稿
     */
    DRAFT((byte)3),
    /**
     * 待审核
     */
    VERIFY((byte)2),
    /**
     * 正常
     */
    NORMAL((byte)1),
    /**
     * 禁用
     */
    DISABLE((byte)0),
    /**
     * 删除
     */
    DEL((byte)-1);

    public byte status;

    CmsStatusEnum(byte status){
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
