package top.xujm.modules.config.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Xujm
 */
@Entity
@Table(name = "xujm_platform_scheduled", schema = BaseConstants.DATABASE_SCHEMA)
public class PlatformScheduled {
    private int id;
    private String scheduledCron;
    private String scheduledName;
    private String scheduledCode;
    private byte isEnable;
    private String runIp;
    /**
     * 定时任务状态(1正常2取消)
     */
    private byte scheduledState;

    private ScheduledFuture<?> future;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "scheduled_cron", nullable = false, length = 20)
    public String getScheduledCron() {
        return scheduledCron;
    }

    public void setScheduledCron(String scheduledCron) {
        this.scheduledCron = scheduledCron;
    }

    @Basic
    @Column(name = "scheduled_name", nullable = false, length = 50)
    public String getScheduledName() {
        return scheduledName;
    }

    public void setScheduledName(String scheduledName) {
        this.scheduledName = scheduledName;
    }

    @Basic
    @Column(name = "scheduled_code", nullable = false, length = 10)
    public String getScheduledCode() {
        return scheduledCode;
    }

    public void setScheduledCode(String scheduledCode) {
        this.scheduledCode = scheduledCode;
    }

    @Basic
    @Column(name = "is_enable", nullable = false)
    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
    }
    @Basic
    @Column(name = "run_ip", nullable = false,length = 30)
    public String getRunIp() {
        return runIp;
    }

    public void setRunIp(String runIp) {
        this.runIp = runIp;
    }

    @Transient
    public byte getScheduledState() {
        return scheduledState;
    }

    public void setScheduledState(byte scheduledState) {
        this.scheduledState = scheduledState;
    }
    @Transient
    public ScheduledFuture<?> getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }
}
