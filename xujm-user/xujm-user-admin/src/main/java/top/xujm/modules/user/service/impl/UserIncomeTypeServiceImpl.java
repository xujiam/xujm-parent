package top.xujm.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.modules.user.model.UserIncomeType;
import top.xujm.modules.user.repository.UserIncomeTypeRepository;
import top.xujm.modules.user.service.UserIncomeTypeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserIncomeTypeServiceImpl implements UserIncomeTypeService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserIncomeTypeRepository userIncomeTypeRepository;

    /**
     * 获取收入类型--配置
     * @return
     */
    @Override
    public List<UserIncomeType> selectUserIncomeType() {
        return userIncomeTypeRepository.findAll();
    }

    /**
     * 添加收入类型
     * @return
     */
    @Override
    public void addIncometype(UserIncomeType userIncomeType) {
        userIncomeTypeRepository.save(userIncomeType);
    }

    @Override
    public void editIncomeisEnable(int id, Byte isEnable) {
        userIncomeTypeRepository.editIncomeisEnable(id, isEnable);
    }
}
