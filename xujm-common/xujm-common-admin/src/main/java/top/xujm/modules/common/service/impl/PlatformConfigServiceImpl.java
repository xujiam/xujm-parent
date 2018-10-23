package top.xujm.modules.common.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.redis.ResourceCache;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.repository.PlatformConfigExtendRepository;
import top.xujm.modules.common.service.PlatformConfigService;
import top.xujm.modules.config.model.PlatformConfig;
import top.xujm.modules.config.repository.PlatformConfigRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class PlatformConfigServiceImpl implements PlatformConfigService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PlatformConfigRepository platformConfigRepository;
    @Autowired
    private PlatformConfigExtendRepository platformConfigExtendRepository;

    /**
     * 平台配置表
     * @param page
     * @param limit
     */
    @Override
    public ResultAdminData<List<PlatformConfig>> selectConfigList(int page, int limit, String configKey, String configLocal, String configMark) {
        String sql = "select t from PlatformConfig t where 1=1";
        if (StringUtils.isNotEmpty(configKey)) {
            sql += " and t.configKey like '%" + configKey + "%'";
        }
        if (StringUtils.isNotEmpty(configLocal)) {
            sql += " and t.configLocal =" + configLocal;
        }
        if (StringUtils.isNotEmpty(configMark)) {
            sql += " and t.configMark like '%" + configMark + "%'";
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
        List<PlatformConfig> objecArraytList = query.getResultList();
        em.close();
        return new ResultAdminData(total,objecArraytList);
    }

    /**
     * 添加配置
     * @param platformConfig
     */
    @Override
    public void addConfig(PlatformConfig platformConfig) {
        platformConfigRepository.save(platformConfig);
        ResourceCache.clear("config");
    }

    /**
     * 删除配置
     * @param id
     */
    @Override
    public void delConfig(int id) {
        PlatformConfig p = platformConfigExtendRepository.findFirstById(id);
        if (p != null) {
            platformConfigExtendRepository.delete(p);
            ResourceCache.clear("config");
        }
    }

    /**
     * 根据配置键得到值
     * @param configKey
     * @return
     */
    @Override
    public PlatformConfig selectConfig(String configKey) {
        return platformConfigRepository.findByConfigKey(configKey);
    }
}
