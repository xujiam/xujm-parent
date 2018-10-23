package top.xujm.modules.user.service;

import top.xujm.modules.user.model.UserConsumeType;

import java.util.List;

public interface UserConsumeTypeService {

    List<UserConsumeType> selectUserConsumeType();

    void addConsumetype(UserConsumeType userConsumeType);

    void editisEnable(int id, Byte isEnable);

    void delConsumetype(int id);
}
