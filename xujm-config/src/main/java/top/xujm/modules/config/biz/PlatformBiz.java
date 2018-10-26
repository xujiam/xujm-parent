package top.xujm.modules.config.biz;

import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.modules.config.model.PlatformAdv;
import top.xujm.modules.config.model.PlatformLink;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/26
 */
public interface PlatformBiz {

    List<PlatformAdv> getAdvList(String position, BooleanTypeEnum booleanTypeEnum);

    List<PlatformLink> getPlatformLinkList(BooleanTypeEnum booleanTypeEnum);

}
