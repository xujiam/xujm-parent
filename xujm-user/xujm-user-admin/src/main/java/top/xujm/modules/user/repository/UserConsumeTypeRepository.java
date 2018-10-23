package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserConsumeType;

public interface UserConsumeTypeRepository extends JpaRepository<UserConsumeType,Integer> {


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserConsumeType set isEnable=:isEnable where id = :id")
    void editisEnable(@Param("id") int var1, @Param("isEnable") Byte var2);
}
