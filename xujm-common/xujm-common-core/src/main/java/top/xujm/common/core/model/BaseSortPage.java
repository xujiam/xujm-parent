package top.xujm.common.core.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Xujm
 * 2018/9/18
 */
public class BaseSortPage extends ListPage {

    @ApiModelProperty(value = "排序字段")
    protected String sorts;
    @ApiModelProperty(value = "排序方向(desc倒序asc正序)")
    protected String direction;

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
