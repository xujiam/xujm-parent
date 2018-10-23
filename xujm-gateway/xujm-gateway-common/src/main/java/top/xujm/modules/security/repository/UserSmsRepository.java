package top.xujm.modules.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.security.model.UserSms;

import java.util.List;

/**
 * @author Xujm
 */
public interface UserSmsRepository extends JpaRepository<UserSms,Long> {

    /**
     * 通过手机号和类型查看验证码发送数据
     * @param mobile 手机号
     * @param type 类型
     * @return UserSms
     */
    UserSms findFirstByMobileAndTypeOrderByIdDesc(String mobile, Byte type);

    /**
     * 查询在该发送时间后的所有数据
     * @param mobile 区号+手机号
     * @param sendTime 发送时间
     * @return 列表
     */
    List<UserSms> findAllByMobileAndSendTimeGreaterThanEqual(String mobile, Long sendTime);
}
