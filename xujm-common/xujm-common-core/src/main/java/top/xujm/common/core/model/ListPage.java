package top.xujm.common.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Xujm
 */
@ApiModel("分页实体")
public class ListPage {

    @ApiModelProperty(value = "分页起始位置",name = "index",example = "0")
    private Integer index = 0;
    @ApiModelProperty(value = "每页条数",name = "count",example = "10")
    private Integer count = 10;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        if(index != null){
            this.index = index;
        }
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        if(count != null){
            this.count = count;
        }
    }
}
