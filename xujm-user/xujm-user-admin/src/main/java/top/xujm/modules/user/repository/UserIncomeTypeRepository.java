package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.modules.user.model.UserIncomeType;

public interface UserIncomeTypeRepository extends JpaRepository<UserIncomeType,Integer> {

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update UserIncomeType set isEnable=:isEnable where id = :id")
    void editIncomeisEnable(@Param("id") int var1, @Param("isEnable") Byte var2);
}
