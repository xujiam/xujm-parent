package top.xujm.modules.user.service;

import top.xujm.modules.user.model.UserIncomeType;

import java.util.List;

public interface UserIncomeTypeService {


    List<UserIncomeType> selectUserIncomeType();

    void addIncometype(UserIncomeType userIncomeType);

    void editIncomeisEnable(int id, Byte isEnable);
}
