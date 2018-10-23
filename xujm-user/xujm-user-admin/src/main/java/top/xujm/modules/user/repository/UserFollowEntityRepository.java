package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.user.model.UserFollow;

public interface UserFollowEntityRepository extends JpaRepository<UserFollow, Integer> {

    /*@Query(value ="select new UserFollow(f.id, u.userId, f.toId, u.account, u.nickname, u.avatar, f.addTime, u.role)" +
            " from UserFollow f,UserInfo u where f.userId = u.userId and u.account = :account")
    Page<UserFollow> selectUserFollow(@Param("account")String account, Pageable pageable);


    @Query(value = "select new UserFollow(f.id, u.userId, f.toId, u.account, u.nickname, u.avatar, f.addTime, u.role)" +
            " from UserFollow f,UserInfo u where f.userId = u.userId")
    Page<UserFollow> selectUserFollow(Pageable pageable);*/

}
