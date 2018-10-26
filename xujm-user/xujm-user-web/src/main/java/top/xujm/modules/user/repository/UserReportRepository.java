package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserReport;

/**
 * @author Xujm
 */
public interface UserReportRepository extends JpaRepository<UserReport,Integer> {
}
