package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserLabel;

import java.util.List;

/**
 * @author Xujm
 */
public interface UserLabelRepository extends JpaRepository<UserLabel,Integer> {

    List<UserLabel> findByIsShowOrderBySortsDesc(byte isShow);

}
