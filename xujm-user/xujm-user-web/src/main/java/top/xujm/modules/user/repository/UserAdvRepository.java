package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserAdv;

import java.util.List;

/**
 * @author Xujm
 */
public interface UserAdvRepository extends JpaRepository<UserAdv,Integer> {

    List<UserAdv> findByPositionAndIsShowOrderBySortsDesc(String position, byte isShow);

}
