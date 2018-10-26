package top.xujm.modules.config.biz.impl;

import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.modules.config.biz.PlatformBiz;
import top.xujm.modules.config.model.PlatformAdv;
import top.xujm.modules.config.model.PlatformLink;
import top.xujm.modules.config.repository.PlatformAdvRepository;
import top.xujm.modules.config.repository.PlatformLinkRepository;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/26
 */
@Component
public class PlatformBizImpl implements PlatformBiz {

    @Autowired
    private PlatformAdvRepository platformAdvRepository;
    @Autowired
    private PlatformLinkRepository platformLinkRepository;

    @Override
    public List<PlatformAdv> getAdvList(String position,BooleanTypeEnum booleanTypeEnum){
        if(booleanTypeEnum == null){
            return platformAdvRepository.findAll();
        }
        return platformAdvRepository.findByPositionAndIsShowOrderBySortsDesc(position,booleanTypeEnum.getType());
    }


    @Override
    public List<PlatformLink> getPlatformLinkList(BooleanTypeEnum booleanTypeEnum){
        if(booleanTypeEnum == null){
            return platformLinkRepository.findAll();
        }
        return platformLinkRepository.findByIsShowOrderBySortsDesc(booleanTypeEnum.getType());

    }


}
