package top.xujm.modules.common.model;

/**
 * @author ZhengYP
 */
public class PageBean {

    private Integer page = 0;

    private Integer limit = 10;

    public PageBean() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page != null) {
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if (limit != null) {
            this.limit = limit;
        }
    }
}
