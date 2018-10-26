package top.xujm.modules.cms.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.core.base.BaseConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@ApiModel("内容实体")
@Entity
@Table(name = "xujm_cms_article", schema = BaseConstants.DATABASE_SCHEMA)
public class CmsArticle extends CmsBase{
    @ApiModelProperty(hidden = true)
    private int id;
    private String langCode;
    private String keywords;
    private String content;
    private String extend;
    private byte isEnable;

    public CmsArticle(){

    }

    public CmsArticle(int cmsId,int userId,String title,String description,String cover,int categoryId,int readNum
            ,int commentNum,int likeNum,long addTime,String content,String extend){
        super(cmsId,userId,title,description,categoryId,readNum ,commentNum,likeNum,addTime,(short) 0,(byte)0,null,cover);
        this.content = content;
        this.extend = extend;
    }

    public CmsArticle(int cmsId,int userId,String title,String description,String cover,int categoryId,int readNum
            ,int commentNum,int likeNum,long addTime){
        this(cmsId,userId,title,description,cover,categoryId,readNum
            ,commentNum,likeNum,addTime,null,null);
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Basic
    @Column(name = "cms_id", nullable = false)
    public int getCmsId() {
        return cmsId;
    }

    @Override
    public void setCmsId(int cmsId) {
        this.cmsId = cmsId;
    }

    @Basic
    @Column(name = "lang_code", nullable = false, length = 10)
    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    @Override
    @Basic
    @Column(name = "title", nullable = false, length = 200)
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "keywords", nullable = false, length = 255)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    @Basic
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "extend", nullable = false, length = 255)
    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Basic
    @Column(name = "is_enable", nullable = false)
    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
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

}
