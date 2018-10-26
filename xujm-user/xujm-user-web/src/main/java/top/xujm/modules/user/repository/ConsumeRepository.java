package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserConsume;

/**
 * @author Xujm
 */
public interface ConsumeRepository extends JpaRepository<UserConsume,Integer> {
}
