package top.xujm.modules.cms.model;

import javax.persistence.*;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_cms", schema = "weking")
public class Cms extends CmsBase{

    @Override
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "cms_id", nullable = false)
    public int getCmsId() {
        return cmsId;
    }

    @Override
    public void setCmsId(int cmsId) {
        this.cmsId = cmsId;
    }

    @Override
    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    @Basic
    @Column(name = "title", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    @Basic
    @Column(name = "description", nullable = false, length = 255)
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    @Basic
    @Column(name = "category_id", nullable = false)
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    @Basic
    @Column(name = "read_num", nullable = false)
    public int getReadNum() {
        return readNum;
    }

    @Override
    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    @Override
    @Basic
    @Column(name = "comment_num", nullable = false)
    public int getCommentNum() {
        return commentNum;
    }

    @Override
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    @Basic
    @Column(name = "like_num", nullable = false)
    public int getLikeNum() {
        return likeNum;
    }

    @Override
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    @Basic
    @Column(name = "sorts", nullable = false)
    public short getSorts() {
        return sorts;
    }

    @Override
    public void setSorts(short sorts) {
        this.sorts = sorts;
    }

    @Override
    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    @Override
    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    @Basic
    @Column(name = "add_time", nullable = false)
    public long getAddTime() {
        return addTime;
    }

    @Override
    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    @Basic
    @Column(name = "template", nullable = false)
    public String getTemplate() {
        return template;
    }

    @Override
    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    @Basic
    @Column(name = "cover", nullable = false)
    public String getCover() {
        return cover;
    }

    @Override
    public void setCover(String cover) {
        this.cover = cover;
    }

}
