package top.xujm.modules.user.common;

import top.xujm.config.resource.ResourcesLang;
import top.xujm.core.language.Language;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibPropertiesUtil;
import top.xujm.core.utils.SpringContextUtil;
import top.xujm.modules.user.model.UserConsumeType;
import top.xujm.modules.user.model.UserIncomeType;
import top.xujm.modules.user.model.UserRole;
import top.xujm.modules.user.repository.UserRoleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public class UserResourceUtil {

    public static final Map<String, UserConsumeType> CONSUME_TYPE_MAP = new HashMap<>();

    public static Map<String, UserIncomeType> IncomeTypeMap = new HashMap<>();

    public static Map<String,String> UserExtendKey = new HashMap<>();

    public static Map<Integer,String> UserRoleMap = new HashMap<>();

    public static boolean accountRandom = LibConverterUtil.toBoolean(LibPropertiesUtil.getConfig("user.account.create.random"));

    public static String getUserRoleName(int roleId){
        if(roleId == 0){
            return Language.getMsg("user.default.role.name");
        }
        String roleName = UserRoleMap.get(roleId);
        if(roleName == null){
            List<UserRole> list = getUserRoleRepository().findAll();
            for (UserRole info:list){
                if(roleId == info.getRoleId()){
                    roleName = ResourcesLang.getLang(info.getRoleName());
                }
                UserRoleMap.put(info.getRoleId(),info.getRoleName());
            }
        }
        return ResourcesLang.getLang(roleName);
    }

    private static UserRoleRepository getUserRoleRepository(){
        UserRoleRepository userRoleRepository = (UserRoleRepository) SpringContextUtil.getBean("userRoleRepository");
        if(userRoleRepository == null){
            throw new RuntimeException("userRoleRepository not found");
        }
        return userRoleRepository;
    }

}
