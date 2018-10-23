package top.xujm.modules.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.security.model.LoginMode;

import java.util.List;

/**
 * @author Xujm
 */
public interface LoginModeRepository extends JpaRepository<LoginMode,Integer>{

    List<LoginMode> findByIsEnable(byte isEnable);

}
