package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserFeedback;

/**
 * @author Xujm
 */
public interface FeedbackRepository extends JpaRepository<UserFeedback,Integer> {
}
