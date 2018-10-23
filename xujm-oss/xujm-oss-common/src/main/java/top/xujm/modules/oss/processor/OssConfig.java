package top.xujm.modules.oss.processor;

/**
 * @author Xujm
 */
public class OssConfig {

    private String accessId;

    private String accessSecret;

    private String fileBucketName;

    private String videoBucketName;

    private String dealBean;

    private boolean isEnable;

    private String endpoint;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getFileBucketName() {
        return fileBucketName;
    }

    public void setFileBucketName(String fileBucketName) {
        this.fileBucketName = fileBucketName;
    }

    public String getVideoBucketName() {
        return videoBucketName;
    }

    public void setVideoBucketName(String videoBucketName) {
        this.videoBucketName = videoBucketName;
    }

    public String getDealBean() {
        return dealBean;
    }

    public void setDealBean(String dealBean) {
        this.dealBean = dealBean;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
