package top.xujm.modules.user.service;

import top.xujm.modules.user.model.UserPocket;

import java.util.List;

public interface UserPocketService {

    List<UserPocket> getUserPocket(String account, String nickname);

    void recharge(String account, String addNum);
}
