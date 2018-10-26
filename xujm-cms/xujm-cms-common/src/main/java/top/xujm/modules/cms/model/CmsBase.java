package top.xujm.modules.cms.model;


import top.xujm.config.resource.ResourceConfig;
import top.xujm.modules.user.model.UserBaseInfo;

/**
 * @author Xujm
 */
public class CmsBase extends UserBaseInfo {

    protected int cmsId;
    protected String title;
    protected String description;
    protected String cover;
    protected int categoryId;
    protected int readNum;
    protected int commentNum;
    protected int likeNum;
    protected short sorts;
    protected byte status;
    protected String template;
    protected String url;
    protected long addTime;

    public void copy(CmsBase cmsBase){
        this.cmsId = cmsBase.getCmsId();
        this.userId = cmsBase.getUserId();
        this.categoryId = cmsBase.getCategoryId();
        this.readNum = cmsBase.getReadNum();
        this.commentNum = cmsBase.getCommentNum();
        this.likeNum = cmsBase.getLikeNum();
        this.sorts = cmsBase.getSorts();
        this.status = cmsBase.getStatus();
        this.template = cmsBase.getTemplate();
        this.addTime = cmsBase.getAddTime();
        this.url = cmsBase.getUrl();
        this.cover = cmsBase.getCover();
    }

    public CmsBase(){

    }

    public CmsBase(int cmsId,int userId,String title,String description,int categoryId,int readNum
            ,int commentNum,int likeNum,long addTime,short sorts,byte status,String template,String cover){
        this.cmsId = cmsId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.readNum = readNum;
        this.commentNum = commentNum;
        this.likeNum = likeNum;
        this.sorts = sorts;
        this.status = status;
        this.template = template;
        this.url = getUrl(cmsId);
        this.addTime = addTime;
        this.cover = cover;
    }

    public int getCmsId() {
        return cmsId;
    }

    public void setCmsId(int cmsId) {
        this.cmsId = cmsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public short getSorts() {
        return sorts;
    }

    public void setSorts(short sorts) {
        this.sorts = sorts;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getUrl() {
        return getUrl(cmsId);
    }

    private String getUrl(int cmsId){
        return String.format("%scms/detail/%d.html", ResourceConfig.getConfig("server_ip"),cmsId);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
