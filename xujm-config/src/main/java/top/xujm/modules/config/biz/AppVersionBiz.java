package top.xujm.modules.config.biz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.modules.config.model.AppVersion;
import top.xujm.modules.config.repository.AppVersionRepository;

/**
 * @author Xujm
 */
@Component
public class AppVersionBiz {

    @Autowired
    private AppVersionRepository appVersionRepository;

    public AppVersion verifyAppVersion(String appCode, String appVersion){
        AppVersion appInfo = getAppInfo(appCode);
        if(appInfo == null){
            return null;
        }
        if(StringUtils.equals(appVersion,appInfo.getAppVersion())){
            return null;
        }
        String[] clientVersion = getVersion(appVersion);
        String[] serverVersion = getVersion(appInfo.getAppVersion());
        for (int i = 0;i < 3;i++){
            if(LibConverterUtil.toInt(serverVersion[i]) > LibConverterUtil.toInt(clientVersion[i])){
                return appInfo;
            }
        }
        return null;
    }

    private AppVersion getAppInfo(String appCode){
        return appVersionRepository.findFirstByAppCodeOrderByIdDesc(appCode);
    }

    private String[] getVersion(String appVersion){
        String[] clientVersion = StringUtils.split(appVersion,".");
        String[] version = new String[3];
        int len = clientVersion.length;
        System.arraycopy(clientVersion, 0, version, 0, len);
        if(len == 1){
            version[1] = "0";
            version[2] = "0";
        }else if(len == 2){
            version[2] = "0";
        }
        return version;
    }


}
