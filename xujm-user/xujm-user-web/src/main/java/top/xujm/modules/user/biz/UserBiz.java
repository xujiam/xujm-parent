package top.xujm.modules.user.biz;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.enums.SexTypeEnum;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.facade.file.FileService;
import top.xujm.modules.user.common.UserRedisCacheKeyEnum;
import top.xujm.modules.user.common.UserRemindException;
import top.xujm.modules.user.common.UserResultEnum;
import top.xujm.modules.user.model.UserBaseInfo;
import top.xujm.modules.user.model.UserExtend;
import top.xujm.modules.user.model.UserInfo;
import top.xujm.modules.user.model.UserPocket;
import top.xujm.modules.user.repository.PocketRepository;
import top.xujm.modules.user.repository.UserExtendRepository;
import top.xujm.modules.user.repository.UserRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Xujm
 */
@Component
public class UserBiz {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PocketRepository pocketRepository;
    @Autowired
    private UserExtendRepository userExtendRepository;
    @Autowired(required = false)
    private FileService fileService;
    @Autowired
    private RedisTemplate<String,?> redisTemplate;

    public UserInfo getUserInfo(int userId){
        String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
        Map<Object,Object> userRedisMap = redisTemplate.opsForHash().entries(cacheKey);
        if(userRedisMap.size() > 0){
            return (UserInfo) LibBeanUtil.mapToObject(userRedisMap,UserInfo.class);
        }
        UserInfo userInfo = userRepository.findFirstByUserId(userId);
        if(userInfo == null){
            return null;
        }
        cacheUserInfo(cacheKey,userInfo);
        return userInfo;
    }

    public void verifyUserExist(int userId){
        if(userId == 0){
            throw new UserRemindException(UserResultEnum.USER_NOT_EXIST);
        }
        UserInfo userInfo = getUserInfo(userId);
        if(userInfo == null){
            throw new UserRemindException(UserResultEnum.USER_NOT_EXIST);
        }
    }

    private boolean verifyUserCacheExist(int userId){
        String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
        Boolean isHasKey = redisTemplate.hasKey(cacheKey);
        return isHasKey != null && isHasKey;
    }

    public String getUserInfoByUserId(int userId,String field){
        String fieldVal = getUserCacheInfoByUserId(userId,field);
        if(fieldVal != null){
            return fieldVal;
        }
        UserInfo userInfo = userRepository.findFirstByUserId(userId);
        if(userInfo == null){
            return null;
        }
        Map<String,String> userInfoMap = cacheUserInfo(UserRedisCacheKeyEnum.USER.getKey(userId),userInfo);
        return userInfoMap.get(field);
    }

    private String getUserCacheInfoByUserId(int userId,String field){
        String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
        Object object = redisTemplate.opsForHash().get(cacheKey,field);
        if(object != null){
            return LibConverterUtil.toString(object);
        }
        return null;
    }

    /**
     * 获得用户单个扩展信息
     */
    public String getUserExtendInfoByUserId(int userId, String field) {
        String userExtentInfo = getUserCacheInfoByUserId(userId,field);
        if (userExtentInfo == null) {
            List<UserExtend> list = userExtendRepository.findByUserId(userId);
            for (UserExtend info : list) {
                cacheUserInfo(userId,info.getExtendKey(),info.getExtendValue());
                if(StringUtils.equals(info.getExtendKey(),field)){
                    userExtentInfo = info.getExtendValue();
                }
            }
        }
        return userExtentInfo;
    }

    /**
     * 获得用户扩展信息
     */
    public Map<String,String> getUserExtendInfoByUserId(int userId, String... fields) {
        Map<String,String> userExtentInfo = getUserCacheInfoByUserId(userId,fields);
        if (userExtentInfo == null) {
            userExtentInfo = new HashMap<>();
            List<UserExtend> list = userExtendRepository.findByUserId(userId);
            for (UserExtend info : list) {
                cacheUserInfo(userId,info.getExtendKey(),info.getExtendValue());
                userExtentInfo.put(info.getExtendKey(),info.getExtendValue());
            }
        }
        return userExtentInfo;
    }


    public Map<String,String> getUserInfoByUserId(int userId,String... fields){
        Map<String,String> userInfoMap = getUserCacheInfoByUserId(userId,fields);
        if(userInfoMap != null){
            return userInfoMap;
        }
        UserInfo userInfo = userRepository.findFirstByUserId(userId);
        if(userInfo == null){
            return null;
        }
        return cacheUserInfo(UserRedisCacheKeyEnum.USER.getKey(userId),userInfo);
    }

    /**
     * 从缓存中获取用户字段信息
     */
    private Map<String,String> getUserCacheInfoByUserId(int userId,String... fields){
        if(fields.length == 0){
            return null;
        }
        String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
        List<Object> list = redisTemplate.opsForHash().multiGet(cacheKey,Arrays.asList(fields));
        if (list.get(0) != null) {
            Map<String,String> userInfoMap = new HashMap<>(0);
            int len = fields.length;
            for (int i = 0; i < len; i++) {
                userInfoMap.put(fields[i], LibConverterUtil.toString(list.get(i)));
            }
            return userInfoMap;
        }
        return null;
    }

    public UserBaseInfo getUserBaseInfoByUserId(int userId){
        Map<String,String> userBaseInfoMap = getUserInfoByUserId(userId,LibBeanUtil.getBeanAttributeName(UserBaseInfo.class));
        if(userBaseInfoMap != null){
            return (UserBaseInfo) LibBeanUtil.mapToObject(userBaseInfoMap,UserBaseInfo.class);
        }
        return null;
    }

    public String getUserLangCode(int userId){
        return getUserInfoByUserId(userId,"langCode");
    }

    private Map<String,String> cacheUserInfo(String cacheKey,UserInfo userInfo){
        Map<String,String> userInfoMap = LibBeanUtil.objectToMap(userInfo);
        redisTemplate.opsForHash().putAll(cacheKey,userInfoMap);
        redisTemplate.expire(cacheKey,UserRedisCacheKeyEnum.USER.getExpireTime(), TimeUnit.DAYS);
        return userInfoMap;
    }

    /**
     * 缓存用户单个信息
     * @param userId  用户ID
     * @param fieldKey 用户字段KEY
     * @param fieldValue 用户字段值
     */
    public void cacheUserInfo(int userId,String fieldKey,String fieldValue){
        UserInfo userInfo = getUserInfo(userId);
        if(userInfo == null){
            throw new UserRemindException(UserResultEnum.USER_NOT_EXIST);
        }
        String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
        redisTemplate.opsForHash().put(cacheKey,fieldKey,fieldValue);
    }

    public void cacheUserInfo(int userId,String fieldKey,long incrementNum){
        if(verifyUserCacheExist(userId)){
            String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
            redisTemplate.opsForHash().increment(cacheKey,fieldKey,incrementNum);
        }
    }

    public void clearUserInfo(int userId){
        String cacheKey = UserRedisCacheKeyEnum.USER.getKey(userId);
        if(userId == 0){
            redisTemplate.delete(StringUtils.replace(cacheKey,String.format(":%d",userId),""));
        }else {
            redisTemplate.opsForHash().delete(cacheKey);
        }
    }

    public void updateUserSex(int userId,String sex){
        //性别只有为男，女才可以修改
        Byte s = LibConverterUtil.toByte(sex);
        if(s == SexTypeEnum.MAN.getValue() || s == SexTypeEnum.WOMAN.getValue()){
            boolean isUpdate = true;
            if(!LibConverterUtil.toBoolean(ResourceConfig.getConfig("user.sex.many.times"))){
                String cacheSex = getUserInfoByUserId(userId,"sex");
                isUpdate = LibConverterUtil.toByte(cacheSex) == SexTypeEnum.UNKNOWN.getValue();
            }
            //性别只能修改一次，如已为男女则不能修改
            if(isUpdate){
                int r = userRepository.updateUserSex(s,userId);
                if(r > 0){
                    cacheUserInfo(userId,"sex",sex);
                }
            }
        }
    }

    public void updateUserNickname(int userId,String nickname){
        int r = userRepository.updateUserNickname(nickname,userId);
        if(r > 0){
            cacheUserInfo(userId,"nickname",nickname);
        }
    }

    public void updateUserAccount(int userId,String account){
        Integer accountUserId = userRepository.selectUserIdByAccount(account);
        if(accountUserId != null){
            throw new UserRemindException(UserResultEnum.ACCOUNT_EXIST);
        }
        int r = userRepository.updateUserAccount(account,userId);
        if(r > 0){
            cacheUserInfo(userId,"account",account);
        }
    }

    public void updateUserSignature(int userId,String signature){
        int r = userRepository.updateUserSignature(signature,userId);
        if(r > 0){
            cacheUserInfo(userId,"signature",signature);
        }
    }

    public void updateUserAvatar(int userId,String avatar){
        int r = userRepository.updateUserAvatar(avatar,userId);
        if(r > 0){
            cacheUserInfo(userId,"avatar",avatar);
        }
    }

    public void updateUserCity(int userId,String city){
        int r = userRepository.updateUserCity(city,userId);
        if(r > 0){
            cacheUserInfo(userId,"city",city);
        }
    }

    public void updateUserCid(int userId,String cid){
        int r = userRepository.updateUserCid(cid,userId);
        if(r > 0){
            cacheUserInfo(userId,"cid",cid);
        }
    }

    public void updateUserLangCode(int userId,String langCode){
        if(!StringUtils.equals(langCode,getUserLangCode(userId))){
            int r = userRepository.updateUserLangCode(langCode,userId);
            if(r > 0){
                cacheUserInfo(userId,"langCode",langCode);
            }
        }
    }

    public void updateUserBirthday(int userId,String birthday){
        if(!LibDateUtil.isValidDate(birthday, LibDateUtil.secondDate)){
            throw new UserRemindException(UserResultEnum.BIRTHDAY_ERROR);
        }
        int r = userRepository.updateUserBirthday(birthday,userId);
        if(r > 0){
            cacheUserInfo(userId,"birthday",birthday);
        }
    }

    public void updateUserLngAndLat(int userId,String lng,String lat){
        Double nd = LibConverterUtil.toDouble(lng);
        Double ad = LibConverterUtil.toDouble(lat);
        int r = userRepository.updateUserLngAndLat(nd,ad,userId);
        if(r > 0){
            cacheUserInfo(userId,"lng",lng);
            cacheUserInfo(userId,"lat",lat);
        }
    }

    public void updateUserFansNum(int userId,int incNum){
        int r = userRepository.updateUserFansNum(userId,incNum);
        if(r > 0){
            cacheUserInfo(userId,"fansNum",incNum);
        }
    }

    public void updateUserFollowNum(int userId,int incNum){
        int r = userRepository.updateUserFollowNum(userId,incNum);
        if(r > 0){
            cacheUserInfo(userId,"followNum",incNum);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public UserInfo register(int userId,String account,String nickname,String avatar,String inviteCode,String langCode){
        UserInfo user = new UserInfo();
        user.setUserId(userId);
        user.setAccount(account);
        if(StringUtils.isEmpty(nickname)){
           nickname = account;
        }
        user.setNickname(nickname);
        if(!StringUtils.isEmpty(avatar)){
            avatar = getUserAvatar(avatar);
            user.setAvatar(avatar);
        }
        user.setLangCode(langCode);
        user.setInviteCode(inviteCode);
        user.setAddTime(LibDateUtil.getNowTime());
        userRepository.save(user);
        registerPocket(userId);
        return user;
    }

    private void registerPocket(int userId){
        UserPocket userPocket = new UserPocket();
        userPocket.setUserId(userId);
        pocketRepository.save(userPocket);
    }

    public String getUserAvatar(String avatar){
        if(LibSysUtil.isUrl(avatar)){
            if(fileService != null){
                JSONObject file = fileService.saveFile(avatar);
                if(file == null){
                    return null;
                }
                return file.getString("fileUrl");
            }
            return null;
        }
        return avatar;
    }


}
