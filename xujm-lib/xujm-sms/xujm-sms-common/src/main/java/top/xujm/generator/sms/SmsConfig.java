package top.xujm.generator.sms;

/**
 * @author Xujm
 */
public class SmsConfig {

    private String cnUn;

    private String cnPw;

    private String un;

    private String pw;

    private boolean isEnable;

    private String sendBean;

    public String getCnUn() {
        return cnUn;
    }

    public void setCnUn(String cnUn) {
        this.cnUn = cnUn;
    }

    public String getCnPw() {
        return cnPw;
    }

    public void setCnPw(String cnPw) {
        this.cnPw = cnPw;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getSendBean() {
        return sendBean;
    }

    public void setSendBean(String sendBean) {
        this.sendBean = sendBean;
    }


}
