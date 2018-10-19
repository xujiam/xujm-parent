package top.xujm.schedule;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Xujm
 */
public class ScheduledInfo {
    /**
     * 定时任务名称
     */
    private String scheduledName;
    /**
     * 定时任务代号
     */
    private String scheduledCode;
    /**
     * 定时任务时间
     */
    private String scheduledCron;
    /**
     * 定时任务状态(1正常2取消)
     */
    private byte scheduledState;
    /**
     * 是否启用
     */
    private byte isEnable;

    private ScheduledFuture<?> future;

    public String getScheduledName() {
        return scheduledName;
    }

    public void setScheduledName(String scheduledName) {
        this.scheduledName = scheduledName;
    }

    public String getScheduledCode() {
        return scheduledCode;
    }

    public void setScheduledCode(String scheduledCode) {
        this.scheduledCode = scheduledCode;
    }

    public String getScheduledCron() {
        return scheduledCron;
    }

    public void setScheduledCron(String scheduledCron) {
        this.scheduledCron = scheduledCron;
    }

    public byte getScheduledState() {
        return scheduledState;
    }

    public void setScheduledState(byte scheduledState) {
        this.scheduledState = scheduledState;
    }

    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
    }

    public ScheduledFuture<?> getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }
}
