package top.xujm.modules.user.service;

import top.xujm.modules.user.model.UserBlack;

import java.util.List;

/**
 * @author Xujm
 */
public interface BlackService {

    /**
     * 拉黑
     * @param account 用户account
     */
    void pull(String account);

    /**
     * 取消拉黑
     * @param account 用户account
     */
    void cancel(String account);

    List<UserBlack> list();

    boolean verifyBlack(int userId, int toId);

    boolean verifyBlack(int userId, String account);

    void checkBlack(int userId, int toId);

}
