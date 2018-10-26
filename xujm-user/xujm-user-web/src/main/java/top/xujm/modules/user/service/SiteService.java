package top.xujm.modules.user.service;

import top.xujm.modules.user.model.UserAdv;
import top.xujm.modules.user.model.UserReport;

import java.util.List;

/**
 * @author Xujm
 */
public interface SiteService {

    void feedback(String content, String contactWay);

    void report(UserReport userReport);

    List<UserAdv> getAdvList(String position);

}
