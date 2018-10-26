package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserIncomeType;

/**
 * @author Xujm
 */
public interface IncomeTypeRepository extends JpaRepository<UserIncomeType,Integer> {
}
