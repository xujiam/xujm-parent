package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserIncome;

/**
 * @author Xujm
 */
public interface IncomeRepository extends JpaRepository<UserIncome,Integer> {
}
