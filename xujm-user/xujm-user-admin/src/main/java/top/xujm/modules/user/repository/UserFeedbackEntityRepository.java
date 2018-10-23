package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserFeedback;

public interface UserFeedbackEntityRepository extends JpaRepository<UserFeedback,Integer> {

    UserFeedback findFirstById(int var1);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserFeedback set status=1 where id = :id")
    void reviewFeedBack(@Param("id") int var1);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserFeedback set status=0 where id = :id")
    void ignoreFeedBack(@Param("id") int var1);
}
