package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.common.core.model.ResultData;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.modules.auth.model.AdminRole;
import top.xujm.modules.auth.model.AdminUserRole;
import top.xujm.modules.auth.repository.AdminRoleRepository;
import top.xujm.modules.auth.repository.AdminUserRoleRepository;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.DateUtils;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.security.common.SecurityRemindException;
import top.xujm.modules.security.common.SecurityResultEnum;
import top.xujm.modules.security.model.UserAccount;
import top.xujm.modules.security.service.AccountService;
import top.xujm.modules.user.model.UserAccountEntity;
import top.xujm.modules.user.model.UserAccountExtend;
import top.xujm.modules.user.model.UserInfo;
import top.xujm.modules.user.repository.UserAccountEntityRepository;
import top.xujm.modules.user.repository.UserInfoEntityRepository;
import top.xujm.modules.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserAccountEntityRepository userAccountEntityRepository;
    @Autowired
    private UserInfoEntityRepository userInfoEntityRepository;
    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    @Autowired
    private AccountService accountService;

    /**
     * 根据条件分页查询用户列表
     * @param page      页数
     * @param limit     每页显示的条数
     * @param account   账号
     * @param nickname  昵称
     * @param sex        性别
     */
    @Override
    public ResultAdminData selectUserList(int page, int limit, String account, String nickname, String sex, String addTime) {
        // 得到图片服务器的路径
        String configValue = ResourceConfig.getConfig("image_server");
        boolean condition = StringUtils.isEmpty(account) && StringUtils.isEmpty(nickname) && StringUtils.isEmpty(sex) && StringUtils.isEmpty(addTime);
                String sql = "select new UserAccountEntity (a.mobile,a.insideRole,a.isBlack,u.addTime,a.userId, u.account, u.nickname, u.avatar,u.sex,u.level, u.signature, u.role)" +
                " from UserAccount a ,UserInfo u where u.userId = a.userId ";
        if (!Objects.isNull(account)) {
            sql += " and u.account like '%"+account + "%'";
        }
        if (!Objects.isNull(nickname)) {
            sql += " and u.nickname like '%"+nickname + "%'";
        }
        if (!Objects.isNull(sex) && !"0".equals(sex)) {
            sql += " and u.sex like '%"+sex + "%'";
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and u.addTime >= " + times[0];
            sql += " and u.addTime <= " + times[1];
        } else if (condition){
            sql += " and u.addTime >= "+ StringUtil.timeToString(DateUtils.getLastWeek() + "000000");
        }
        //创建原生SQL查询QUERY实例
        Query query =  em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;           //如果查询结果少于开始记录，则返回第一页显示
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        //执行查询，返回的是查询属性值数组的集合
        List<UserAccountEntity> objecArraytList = query.getResultList();
        em.close();
        String avatar = null;
        for(UserAccountEntity info : objecArraytList) {
            avatar = configValue + info.getAvatar();
            info.setAvatar(avatar);
        }
        return new ResultAdminData(total,objecArraytList);
    }

    /**
     * 编辑用户
     */
    @Transactional
    @Override
    public void editUser(int userId, String nickname, short insideRole,Byte sex,short role) {
        if (Objects.isNull(sex)) {
            sex = 0;
        }
        UserAccount userInfo = this.userAccountEntityRepository.findFirstByUserId(userId);
        if (userInfo == null) {
            throw new SecurityRemindException(SecurityResultEnum.USER_NOT_EXIST);
        } else {
            this.userAccountEntityRepository.updateUserAccount(userId,insideRole);
            this.userInfoEntityRepository.updateUserInfo(userId, nickname, insideRole, sex, role);
        }
    }

    /**
     * 拉黑用户
     * @param userId    用户ID
     */
    @Override
    public void blackUser(int userId) {
        UserAccount userInfo = this.userAccountEntityRepository.findFirstByUserId(userId);
        if (userInfo == null) {
            throw new SecurityRemindException(SecurityResultEnum.USER_NOT_EXIST);
        } else {
            this.userAccountEntityRepository.blackUserAccount(userId);
        }
    }

    /**
     * 洗白用户
     * @param userId    用户ID
     */
    @Override
    public void removeblack(int userId) {
        UserAccount userInfo = this.userAccountEntityRepository.findFirstByUserId(userId);
        if (userInfo == null) {
            throw new SecurityRemindException(SecurityResultEnum.USER_NOT_EXIST);
        } else {
            this.userAccountEntityRepository.removeblack(userId);
        }
    }

    /**
     * 查询出后台内部用户
     */
    @Override
    public List<UserAccountExtend> selectAccountList() {
        short insideRole = 2;
        List<UserAccount> userAccountlist = userAccountEntityRepository.findUserAccountByInsideRole(insideRole);
        List<UserAccountExtend> result = new ArrayList<>();
        UserAccountExtend userAccountExtend;
        for (UserAccount info:userAccountlist){
            userAccountExtend = LibBeanUtil.entityCopy(info,UserAccountExtend.class);
            if (userAccountExtend.getUserId() != 0){
                List<Integer> adminUserRoles = adminUserRoleRepository.selectRoleIdByUserId(userAccountExtend.getUserId());
                if (adminUserRoles.size() > 0) {
                    StringBuilder roleNames = new StringBuilder();
                    StringBuilder roleKeys = new StringBuilder();
                    for (Integer ru : adminUserRoles) {
                        List<String> roleName = adminRoleRepository.selectRoleNameByroleId(ru);
                        roleNames.append(roleName).append(",");
                        roleKeys.append(ru).append(",");
                    }
                    roleNames = new StringBuilder(roleNames.substring(0, roleNames.length() - 1));
                    userAccountExtend.setRoleNames(roleNames.toString());
                    userAccountExtend.setRoleKeys(roleKeys.toString());
                }
            }
            result.add(userAccountExtend);
        }
        return result;
    }

    /**
     * 获取角色
     */
    @Override
    public List<AdminRole> selectAdminRole() {
        return adminRoleRepository.findAll();
    }

    /**
     * 修改后台用户
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editAccount(int userId, String username, String roles) {
        UserAccount userInfo = this.userAccountEntityRepository.findFirstByUserId(userId);
        if (userInfo == null) {
            throw new SecurityRemindException(SecurityResultEnum.USER_NOT_EXIST);
        } else {
            if (!StringUtils.isEmpty(username)) {
                userAccountEntityRepository.updateUsername(userId, username);
            }
            adminUserRoleRepository.deleteUserRole(userId);
            if (!StringUtils.isEmpty(roles)) {
                saveRoleUser(userId, roles);
            }
        }
    }

    /**
     * 修改账户昵称
     */
    @Override
    public void updateUserNickname(String account, String username) {
        userInfoEntityRepository.updateUserNickname(account, username);
    }

    /**
     * 根据对方id集合查询出用户信息
     */
    @Override
    public List<UserInfo> getUserInfo(List<Integer> toids) {
        List<UserInfo> modules = userInfoEntityRepository.selectUserInfoInUserId(toids);
        return modules;
    }

    /**
     * 根据id获取用户
     */
    @Override
    public ResultData<List<UserAccountEntity>> selectUserById(int objectId) {
        String configValue = ResourceConfig.getConfig("image_server");
        UserAccountEntity u = userAccountEntityRepository.selectUserById(objectId);
        u.setAvatarExtend(configValue + u.getAvatar());
        List<UserAccountEntity> uList = new ArrayList<>();
        uList.add(u);
        return new ResultData(uList);
    }

    /**
     * 修改后台密码
     */
    @Override
    public void editUserPwd(int userId, String newPwd) {
        accountService.updatePwd(userId,newPwd);
    }

    /**
     * 获取用户量
     */
    @Override
    public ResultData selectUserNum() {
        int num = userInfoEntityRepository.selectUserNum();
        return new ResultData<>(num);
    }


    /**
     * 对后台用户进行角色分配
     */
    private void saveRoleUser(int userId, String roles) {
        String[] roleids = roles.split(",");
        List<AdminUserRole> rUsers = new ArrayList<>();
        for (String roleid : roleids) {
            AdminUserRole rUser;
            rUser = new AdminUserRole();
            rUser.setUserId(userId);
            rUser.setRoleId(Integer.parseInt(roleid));
            rUsers.add(rUser);
        }
        adminUserRoleRepository.saveAll(rUsers);
    }

}