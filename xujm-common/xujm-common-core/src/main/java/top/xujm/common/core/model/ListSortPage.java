package top.xujm.common.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Xujm
 */
@ApiModel("分页排序实体")
public class ListSortPage extends BaseSortPage{

    ListSortPage(){
        if(this.sorts == null){
            this.setSorts("id");
        }
        if(this.direction == null){
            this.setDirection("desc");
        }
    }

}
