package top.xujm.modules.user.biz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.modules.user.common.UserResourceUtil;
import top.xujm.modules.user.model.UserConsumeType;
import top.xujm.modules.user.repository.UserConsumeTypeRepository;

import java.util.List;

/**
 * @author Xujm
 */
@Component
public class UserResourceBiz {

    @Autowired
    private UserConsumeTypeRepository userConsumeTypeRepository;

    public UserConsumeType getConsumeType(String consumeCode){
        UserConsumeType userConsumeType = UserResourceUtil.CONSUME_TYPE_MAP.get(consumeCode);
        if(userConsumeType == null){
            List<UserConsumeType> list = userConsumeTypeRepository.findAll();
            for (UserConsumeType info:list){
                if(StringUtils.equals(info.getConsumeCode(),consumeCode)){
                    userConsumeType = info;
                }
                UserResourceUtil.CONSUME_TYPE_MAP.put(info.getConsumeCode(),info);
            }
        }
        return userConsumeType;
    }

}
