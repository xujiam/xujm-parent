package top.xujm.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.xujm.modules.user.model.UserPocket;

import java.util.List;

public interface UserPocketEntityRepository extends JpaRepository<UserPocket,Integer>{


    @Query("select new UserPocket (u.userId, u.account, u.nickname, u.avatar, p.surplusTicket, p.surplusDiamond, p.totalTicket, p.totalDiamond, p.totalConsume, p.frozenDiamond)" +
            " from UserPocket p ,UserInfo u where u.userId = p.userId and u.account = ?1")
    List<UserPocket> selectUserPocketByAccount(String account);

    @Query("select new UserPocket (u.userId, u.account, u.nickname, u.avatar, p.surplusTicket, p.surplusDiamond, p.totalTicket, p.totalDiamond, p.totalConsume, p.frozenDiamond)" +
            " from UserPocket p ,UserInfo u where u.userId = p.userId and u.nickname = ?1")
    List<UserPocket> selectUserPocketByNickname(String nickname);

    @Query("select new UserPocket (u.userId, u.account, u.nickname, u.avatar, p.surplusTicket, p.surplusDiamond, p.totalTicket, p.totalDiamond, p.totalConsume, p.frozenDiamond)" +
            " from UserPocket p ,UserInfo u where u.userId = p.userId and u.account = ?1 and u.nickname = ?2")
    List<UserPocket> selectUserPocketByAccountAndNickname(String account, String nickname);
}
