package top.xujm.modules.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.security.model.Userconnection;

/**
 * @author Xujm
 */
public interface UserconnectionRepository extends JpaRepository<Userconnection,Integer> {

    Userconnection findFirstByUserId(String userId);

}
