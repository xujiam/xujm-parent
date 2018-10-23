package top.xujm.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.modules.user.model.UserConsumeType;
import top.xujm.modules.user.repository.UserConsumeTypeRepository;
import top.xujm.modules.user.service.UserConsumeTypeService;

import java.util.List;

@Component
public class UserConsumeTypeServiceImpl implements UserConsumeTypeService {

    @Autowired
    private UserConsumeTypeRepository userConsumeTypeRepository;


    /**
     * 获取消费类型--配置
     * @return
     */
    @Override
    public List<UserConsumeType> selectUserConsumeType() {
        return userConsumeTypeRepository.findAll();
    }

    /**
     * 添加消费类型
     * @param userConsumeType
     */
    @Override
    public void addConsumetype(UserConsumeType userConsumeType) {
        userConsumeTypeRepository.save(userConsumeType);
    }

    /**
     * 消费类型是否启用
     * @param id
     * @param isEnable
     */
    @Override
    public void editisEnable(int id, Byte isEnable) {
        userConsumeTypeRepository.editisEnable(id, isEnable);
    }

    /**
     * 删除消费类型
     * @param id
     */
    @Override
    public void delConsumetype(int id) {
        UserConsumeType userConsumeType = userConsumeTypeRepository.findById(id).get();
        if (userConsumeType != null) {
            userConsumeTypeRepository.delete(userConsumeType);
        }
    }
}
